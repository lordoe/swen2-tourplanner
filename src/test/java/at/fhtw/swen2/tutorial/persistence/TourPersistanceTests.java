package at.fhtw.swen2.tutorial.persistence;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TourPersistanceTests {

    @Autowired
    private TourRepository tourRepository;

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
        TourEntity tour = TourEntity.builder().name("TourTest").build();
        tour = tourRepository.save(tour);

        // Act
        tourRepository.delete(tour);

        // Assert
        assertFalse(tourRepository.findById(tour.getId()).isPresent());
    }
}
