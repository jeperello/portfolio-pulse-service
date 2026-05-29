package com.jeperello.portfolio_pulse_service.controller;

import com.jeperello.portfolio_pulse_service.dto.AnalyticsEventDTO;
import com.jeperello.portfolio_pulse_service.dto.StatsResponseDTO;
import com.jeperello.portfolio_pulse_service.service.AnalyticsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@Slf4j
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    @PostMapping
    public ResponseEntity<String> receiveEvent(@Valid @RequestBody AnalyticsEventDTO event) {
        log.info("Evento recibido: {} desde la sesión: {}", event.getEventType(), event.getSessionId());
        analyticsService.processEvent(event);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Evento encolado correctamente");
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponseDTO> getStats(
            @RequestParam(required = false) String sessionId) {
        return ResponseEntity.ok(analyticsService.getStats(sessionId));
    }
}
