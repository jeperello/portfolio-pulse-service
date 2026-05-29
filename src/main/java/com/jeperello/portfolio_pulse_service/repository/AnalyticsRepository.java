package com.jeperello.portfolio_pulse_service.repository;


import com.jeperello.portfolio_pulse_service.model.AnalyticsEvent;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyticsRepository extends MongoRepository<AnalyticsEvent, String> {
    // Obtenemos los últimos 10 eventos
    List<AnalyticsEvent> findTop10ByOrderByTimestampDesc();

    // Para los últimos eventos de una sesión específica
    List<AnalyticsEvent> findTop10BySessionIdOrderByTimestampDesc(String sessionId);

    // Contar sesiones únicas
    @Aggregation(pipeline = { "{ '$group': { '_id': '$sessionId' } }", "{ '$count': 'total' }" })
    Long countDistinctSessionId();

    // Para el filtrado opcional
    List<AnalyticsEvent> findBySessionId(String sessionId);
}