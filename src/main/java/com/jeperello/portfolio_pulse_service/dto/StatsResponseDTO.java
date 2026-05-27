package com.jeperello.portfolio_pulse_service.dto;

import com.jeperello.portfolio_pulse_service.model.AnalyticsEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class StatsResponseDTO {
    private long totalEvents;
    private Map<String, Long> eventsByType;
    private List<ComponentCount> topComponents;
    private long uniqueSessions;
    private List<AnalyticsEvent> lastEvents;

    @Data
    @AllArgsConstructor
    public static class ComponentCount {
        private String componentId;
        private long count;
    }
}
