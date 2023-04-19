package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.service.Service;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourServiceImplTest {

    @Autowired
    private Service<Tour> tourService;

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
}