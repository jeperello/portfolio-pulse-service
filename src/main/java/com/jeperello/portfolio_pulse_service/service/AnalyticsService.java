package com.jeperello.portfolio_pulse_service.service;

import com.jeperello.portfolio_pulse_service.dto.AnalyticsEventDTO;
import com.jeperello.portfolio_pulse_service.dto.StatsResponseDTO;
import com.jeperello.portfolio_pulse_service.model.AnalyticsEvent;
import com.jeperello.portfolio_pulse_service.producer.AnalyticsProducer;
import com.jeperello.portfolio_pulse_service.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public StatsResponseDTO getStats() {
        var allEvents = repository.findAll();

        // Agrupación por tipo de evento
        var byType = allEvents.stream()
                .collect(Collectors.groupingBy(AnalyticsEvent::getEventType, Collectors.counting()));

        // Agrupación por componente (Top 5)
        var topComponents = allEvents.stream()
                .collect(Collectors.groupingBy(AnalyticsEvent::getComponentId, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .map(e -> new StatsResponseDTO.ComponentCount(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return StatsResponseDTO.builder()
                .totalEvents(repository.count())
                .eventsByType(byType)
                .topComponents(topComponents)
                .uniqueSessions(allEvents.stream().map(AnalyticsEvent::getSessionId).distinct().count())
                .lastEvents(repository.findTop10ByOrderByTimestampDesc())
                .build();
    }
}