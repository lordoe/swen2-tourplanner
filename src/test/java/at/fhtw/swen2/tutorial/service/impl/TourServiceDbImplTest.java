package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourServiceDbImplTest {

    @Autowired
    private TourService tourService;

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
    public void testUpdate(){
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
        control.setName("Changed");
        Tour updated = tourService.update(control);

        // Assert
        assertEquals("Changed", updated.getName());
        assertEquals(control.getId(), updated.getId());
    }
}