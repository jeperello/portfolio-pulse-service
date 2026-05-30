package com.jeperello.portfolio_pulse_service.service;

import com.jeperello.portfolio_pulse_service.dto.AnalyticsEventDTO;
import com.jeperello.portfolio_pulse_service.dto.SessionResumenDTO;
import com.jeperello.portfolio_pulse_service.dto.StatsResponseDTO;
import com.jeperello.portfolio_pulse_service.model.AnalyticsEvent;
import com.jeperello.portfolio_pulse_service.producer.AnalyticsProducer;
import com.jeperello.portfolio_pulse_service.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticsService {
    private final AnalyticsProducer eventProducer;
    private final AnalyticsRepository repository;

    public void processEvent(AnalyticsEventDTO event) {
        log.debug("Procesando evento para enviar a Kafka: {}", event.getEventType());
        eventProducer.sendEvent(event);
    }

    public StatsResponseDTO getStats(String sessionId) {
        // 1. Obtener los eventos base (filtrados o todos)
        List<AnalyticsEvent> events;
        if (sessionId != null && !sessionId.isBlank()) {
            events = repository.findBySessionId(sessionId);
        } else {
            events = repository.findAll();
        }

        // 2. Procesar estadísticas sobre la lista obtenida
        var byType = events.stream()
                .collect(Collectors.groupingBy(AnalyticsEvent::getEventType, Collectors.counting()));

        var topComponents = events.stream()
                .collect(Collectors.groupingBy(AnalyticsEvent::getComponentId, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .map(e -> new StatsResponseDTO.ComponentCount(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        // 3. Obtener los últimos eventos (con o sin filtro)
        List<AnalyticsEvent> lastEvents = (sessionId != null && !sessionId.isBlank())
                ? repository.findTop10BySessionIdOrderByTimestampDesc(sessionId)
                : repository.findTop10ByOrderByTimestampDesc();

        return StatsResponseDTO.builder()
                .totalEvents(events.size())
                .eventsByType(byType)
                .topComponents(topComponents)
                .uniqueSessions(events.stream().map(AnalyticsEvent::getSessionId).distinct().count())
                .lastEvents(lastEvents)
                .build();
    }

    public List<SessionResumenDTO> getSessionsSummary() {
        List<AnalyticsEvent> allEvents = repository.findAll();

        return allEvents.stream()
                .collect(Collectors.groupingBy(AnalyticsEvent::getSessionId))
                .entrySet().stream()
                .map(entry -> {
                    String sid = entry.getKey();
                    List<AnalyticsEvent> sessionEvents = entry.getValue();

                    // Intentamos extraer el browser de la metadata del primer evento de la sesión
                    String browser = "Unknown";
                    if (!sessionEvents.isEmpty() && sessionEvents.get(0).getMetadata() != null) {
                        Object b = sessionEvents.get(0).getMetadata().get("browser");
                        if (b != null) browser = b.toString();
                    }

                    return SessionResumenDTO.builder()
                            .sessionId(sid)
                            .browser(browser)
                            .eventCount(sessionEvents.size())
                            .build();
                })
                .collect(Collectors.toList());
    }


}