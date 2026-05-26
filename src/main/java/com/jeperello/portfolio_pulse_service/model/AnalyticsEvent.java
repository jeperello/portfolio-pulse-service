package com.jeperello.portfolio_pulse_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "events")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsEvent {
    @Id
    private String id;
    private String eventType;
    private String componentId;
    private Map<String, Object> metadata;
    private Long timestamp;
    private String sessionId;
}