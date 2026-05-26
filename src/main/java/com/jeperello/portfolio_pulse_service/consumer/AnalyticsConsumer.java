package com.jeperello.portfolio_pulse_service.consumer;
import com.jeperello.portfolio_pulse_service.dto.AnalyticsEventDTO;
import com.jeperello.portfolio_pulse_service.model.AnalyticsEvent;
import com.jeperello.portfolio_pulse_service.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AnalyticsConsumer {

    private final AnalyticsRepository repository;

    @KafkaListener(topics = "portfolio-analytics", groupId = "portfolio-group")
    public void consume(AnalyticsEventDTO eventDTO) {
        log.info("Mensaje recibido desde Kafka: {}", eventDTO.getEventType());
        // Mapeamos el DTO al Modelo de persistencia
        AnalyticsEvent event = AnalyticsEvent.builder()
                .eventType(eventDTO.getEventType())
                .componentId(eventDTO.getComponentId())
                .metadata(eventDTO.getMetadata())
                .timestamp(eventDTO.getTimestamp())
                .sessionId(eventDTO.getSessionId())
                .build();
        repository.save(event);
        log.info("Evento persistido en MongoDB con éxito");
    }
}
