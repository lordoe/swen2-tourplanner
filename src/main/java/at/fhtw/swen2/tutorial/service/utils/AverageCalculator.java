package at.fhtw.swen2.tutorial.service.utils;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.springframework.cglib.core.internal.Function;

import java.util.List;

public class AverageCalculator<T> {
    public double calculateAverage(List<T> tList, Function<T, Double> propertyExtractor) {
        double sum = 0.0;
        int count = 0;

        for (T t : tList) {
            Double propertyValue = propertyExtractor.apply(t);
            if(propertyValue != null) {
                sum += propertyValue;
                count++;
            }
        }

        if (count == 0) {
            return 0.0; // or throw an exception, depending on your requirements
        }

        return sum / count;
    }

}
