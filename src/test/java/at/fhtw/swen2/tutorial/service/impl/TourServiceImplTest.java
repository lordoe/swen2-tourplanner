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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourServiceImplTest {

    @Autowired
    private Service<Tour> tourService;

    @Autowired
    private TourLogService tourLogService;

    @Test
    public void testService(){
        // Arrange
        Tour tour = Tour.builder()
                .name("Wanderung")
                .description("langer Hatscher")
                .from("Scheibs")
                .to("Nebraska")
                .distance(33.)
                .estimatedTime(33.)
                .transportType("foot")
                .routeInformation("link")
                .build();

        // Act
        Tour control = tourService.addNew(tour);

        // Assert
        assertNotNull(control);
    }

    @Test
    public void testSaveTourContainingLogs(){
        // Arrange
        TourLog tourLog = TourLog.builder()
                .comment("cool")
                .rating(5)
                .difficulty(Difficulty.EASY)
                .build();
        Tour tour = Tour.builder()
                .name("Wanderung")
                .description("langer Hatscher")
                .from("Scheibs")
                .to("Nebraska")
                .distance(33.)
                .estimatedTime(33.)
                .transportType("foot")
                .routeInformation("link")
                .build();
        tour.addTourLog(tourLog);
        tourLog.setTour(tour);

        // Act
        Tour control = tourService.addNew(tour);
        List<TourLog> tourLogs = tourLogService.findByTourId(control.getId()); // TODO: fix not working

        // Assert
        assertNotNull(control);
        assertNotNull(control.getTourLogs());
        assertEquals(1, tourLogs.size());
        System.out.println(control.getTourLogs().get(0));
        System.out.println(tourLogs);
    }
}