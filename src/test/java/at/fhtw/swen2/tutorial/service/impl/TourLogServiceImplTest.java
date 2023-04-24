package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.persistence.utils.Difficulty;
import at.fhtw.swen2.tutorial.service.Service;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TourLogServiceImplTest {

    @Autowired
    private TourLogService tourLogService;

    @Autowired
    private Service<Tour> tourService;

    @Test
    public void testService(){
        // Arrange
        Tour tour = Tour.builder()
                .name("Wanderung")
                .build();

        TourLog tourLog = TourLog.builder()
                .comment("Wanderung")
                .rating(5)
                .difficulty(Difficulty.EASY)
                .build();
        TourLog tourLog1 = TourLog.builder()
                .comment("Hatscher")
                .rating(8)
                .difficulty(Difficulty.EASY)
                .build();

        // Act
        Tour controlTour = tourService.addNew(tour);
        tourLog.setTour(controlTour);
        tourLog1.setTour(controlTour);
        tourLogService.addNew(tourLog);
        tourLogService.addNew(tourLog1);
        List<TourLog> tourLogs = tourLogService.findAllByTourId(controlTour.getId());

        // Assert
        assertNotNull(controlTour);
        assertNotNull(tourLogs);
        assertEquals(2, tourLogs.size());
        System.out.println(controlTour.getTourLogs());
    }
}