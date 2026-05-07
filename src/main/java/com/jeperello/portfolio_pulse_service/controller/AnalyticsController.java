package com.jeperello.portfolio_pulse_service.controller;

import com.jeperello.portfolio_pulse_service.dto.AnalyticsEventDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@Slf4j // Logger de Lombok
public class AnalyticsController {

    @PostMapping
    public ResponseEntity<String> receiveEvent(@Valid @RequestBody AnalyticsEventDTO event) {
        log.info("Evento recibido: {} desde la sesión: {}", event.getEventType(), event.getSessionId());

        // El siguiente paso será llamar al Service que enviará a Kafka
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Evento encolado correctamente");
    }
}
