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

    // Contar sesiones únicas
    @Aggregation(pipeline = { "{ '$group': { '_id': '$sessionId' } }", "{ '$count': 'total' }" })
    Long countDistinctSessionId();
}