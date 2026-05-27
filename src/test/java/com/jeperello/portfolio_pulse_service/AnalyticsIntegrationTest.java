package com.jeperello.portfolio_pulse_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeperello.portfolio_pulse_service.dto.AnalyticsEventDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// Agregamos esta línea para simular Kafka en los tests
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class AnalyticsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenValidEvent_thenReturns202() throws Exception {
        AnalyticsEventDTO event = AnalyticsEventDTO.builder()
                .eventType("TEST_CLICK")
                .componentId("test-button")
                .metadata(Map.of("os", "linux"))
                .timestamp(System.currentTimeMillis())
                .sessionId("test-session-123")
                .build();

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isAccepted());
    }

    @Test
    void whenInvalidEvent_thenReturns400() throws Exception {
        AnalyticsEventDTO invalidEvent = AnalyticsEventDTO.builder()
                .componentId("test-button")
                .build();

        mockMvc.perform(post("/api/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEvent)))
                .andExpect(status().isBadRequest());
    }
}
