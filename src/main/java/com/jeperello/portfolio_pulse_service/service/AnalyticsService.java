package com.jeperello.portfolio_pulse_service.service;

import com.jeperello.portfolio_pulse_service.dto.AnalyticsEventDTO;
import com.jeperello.portfolio_pulse_service.producer.AnalyticsProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticsService {

    private final AnalyticsProducer eventProducer;

    public void processEvent(AnalyticsEventDTO event) {
        log.debug("Procesando evento para enviar a Kafka: {}", event.getEventType());
        eventProducer.sendEvent(event);
    }
}