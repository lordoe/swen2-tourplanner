package at.fhtw.swen2.tutorial.utils;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.utils.AverageCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AverageCalculatorTest {

    private final List<TourLog> tourLogs;
    private final List<Tour> tours;

    private final double averageTime;
    private final double averageRating;
    private final double averageDistance;

    private final AverageCalculator<TourLog> tourLogAverageCalculator = new AverageCalculator<>();
    private final AverageCalculator<Tour> tourAverageCalculator = new AverageCalculator<>();


    public AverageCalculatorTest(){
        // Arrange
        Integer time0 = 60;
        Integer time1 = 120;
        Integer time2 = 90;
        Integer time3 = 30;
        this.averageTime = (time0 + time1 + time2 + time3) / 4D;

        Integer rating0 = 5;
        Integer rating1 = 8;
        Integer rating2 = 10;
        Integer rating3 = 2;
        this.averageRating = (rating0 + rating1 + rating2 + rating3) / 4D;

        Double distance0 = 100D;
        Double distance1 = 200D;
        Double distance2 = 300D;
        Double distance3 = 400D;
        this.averageDistance = (distance0 + distance1 + distance2 + distance3) / 4D;

        List<TourLog> tourLogs = new ArrayList<>();
        tourLogs.add(TourLog.builder()
                .timeInMinutes(time0)
                .rating(rating0)
                .build());
        tourLogs.add(TourLog.builder()
                .timeInMinutes(time1)
                .rating(rating1)
                .build());
        tourLogs.add(TourLog.builder()
                .timeInMinutes(time2)
                .rating(rating2)
                .build());
        tourLogs.add(TourLog.builder()
                .timeInMinutes(time3)
                .rating(rating3)
                .build());

        List<Tour> tours = new ArrayList<>();
        tours.add(Tour.builder()
                .distance(distance0)
                .build());
        tours.add(Tour.builder()
                .distance(distance1)
                .build());
        tours.add(Tour.builder()
                .distance(distance2)
                .build());
        tours.add(Tour.builder()
                .distance(distance3)
                .build());

        this.tourLogs = tourLogs;
        this.tours = tours;
    }

    @Test
    public void getAverageTime(){
        // Act
        double averageTime = tourLogAverageCalculator.calculateAverage(tourLogs, tourLog -> Double.valueOf(tourLog.getTimeInMinutes()));

        // Assert
        assertEquals(this.averageTime, averageTime);
    }

    @Test
    public void getAverageRating(){
        // Act
        double averageRating = tourLogAverageCalculator.calculateAverage(tourLogs, tourLog -> Double.valueOf(tourLog.getRating()));

        // Assert
        assertEquals(this.averageRating, averageRating);
    }

    @Test
    public void getAverageDistance(){
        // Act
        double averageDistance = tourAverageCalculator.calculateAverage(tours, Tour::getDistance);

        // Assert
        assertEquals(this.averageDistance, averageDistance);
    }

}
