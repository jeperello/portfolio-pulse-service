package com.jeperello.portfolio_pulse_service.producer;

import com.jeperello.portfolio_pulse_service.dto.AnalyticsEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AnalyticsProducer {
    private final KafkaTemplate<String, AnalyticsEventDTO> kafkaTemplate;
    private static final String TOPIC = "portfolio-analytics";

    public void sendEvent(AnalyticsEventDTO event) {
        // Enviamos el evento. Kafka se encarga de la serialización a JSON
        // porque lo configuramos en el application.yml
        kafkaTemplate.send(TOPIC, event.getSessionId(), event);
        log.info("Evento enviado a Kafka topic {}: {}", TOPIC, event.getEventType());
    }
}