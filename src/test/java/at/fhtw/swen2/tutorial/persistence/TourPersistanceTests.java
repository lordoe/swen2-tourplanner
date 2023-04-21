package at.fhtw.swen2.tutorial.persistence;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourLogRepository;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TourPersistanceTests {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourLogRepository tourLogRepository;

    @Test
    public void saveTourTest(){
        // Arrange
        TourEntity tourEntity = TourEntity.builder().build();

        // Act
        tourEntity = tourRepository.save(tourEntity);

        // AssertTrue
        assertTrue(tourRepository.findById(tourEntity.getId()).isPresent());
    }

    @Test
    public void deleteTourTest(){
        // Arrange
        TourEntity tour = TourEntity.builder()
                .name("TourTest")
                .build();
        tour = tourRepository.save(tour);

        // Act
        tourRepository.delete(tour);

        // Assert
        assertFalse(tourRepository.findById(tour.getId()).isPresent());
    }

    @Test
    public void saveTourContainingTourLogs() {
        // Arrange
        TourEntity tourEntity = TourEntity.builder()
                .name("TourTest")
                .build();
        List<TourLogEntity> logEntities = new ArrayList<>();
        logEntities.add(TourLogEntity.builder().comment("mega").tour(tourEntity).build());
        logEntities.add(TourLogEntity.builder().comment("hui").tour(tourEntity).build());
        logEntities.add(TourLogEntity.builder().comment("morgen").tour(tourEntity).build());
        tourEntity.setTourLogs(logEntities);

        // Act
        TourEntity control = tourRepository.save(tourEntity);
        List<TourLogEntity> tourLogs = tourLogRepository.findAllByTourId(tourEntity.getId());

        // Assert
        assertEquals(tourEntity,control);
        assertEquals(logEntities.toString(),tourLogs.toString()); // compare strings because pointer are different
        System.out.println(control.getTourLogs());
    }
}
