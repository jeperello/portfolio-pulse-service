package com.jeperello.portfolio_pulse_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionResumenDTO {
    private String sessionId;
    private String browser;
    private long eventCount;
}
