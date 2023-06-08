package at.fhtw.swen2.tutorial.service;

import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.cglib.core.internal.Function;

import java.util.List;

public interface TourLogService extends Service<TourLog> {

    List<TourLog> findByTourId(Long tourId);

    double calculateAverage(List<TourLog> tourLogs, Function<TourLog, Double> propertyExtractor);

    double getAverageTime();

    double getAverageRating();
}
