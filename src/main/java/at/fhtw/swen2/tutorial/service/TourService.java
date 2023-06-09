package at.fhtw.swen2.tutorial.service;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.springframework.cglib.core.internal.Function;

import java.util.List;

public interface TourService extends Service<Tour> {
    double calculateAverage(List<Tour> tours, Function<Tour, Double> propertyExtractor);
}
