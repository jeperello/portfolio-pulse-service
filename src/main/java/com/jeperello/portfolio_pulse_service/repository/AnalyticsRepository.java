package com.jeperello.portfolio_pulse_service.repository;


import com.jeperello.portfolio_pulse_service.model.AnalyticsEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends MongoRepository<AnalyticsEvent, String> {
}