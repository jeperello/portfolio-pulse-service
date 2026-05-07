package com.jeperello.portfolio_pulse_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsEventDTO {

    @NotBlank(message = "El tipo de evento es obligatorio")
    private String eventType; // ej: "CLICK", "PAGE_VIEW"

    @NotBlank(message = "El componentId es obligatorio")
    private String componentId; // ej: "hero-section", "contact-button"

    private Map<String, Object> metadata; // Datos extra: navegador, resolución, etc.

    @NotNull(message = "El timestamp es obligatorio")
    private Long timestamp;

    @NotBlank(message = "El sessionId es obligatorio")
    private String sessionId;
}
