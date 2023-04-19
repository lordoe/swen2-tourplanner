package at.fhtw.swen2.tutorial.persistence;

import at.fhtw.swen2.tutorial.persistence.entities.PersonEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.persistence.repositories.TourLogRepository;
import at.fhtw.swen2.tutorial.persistence.utils.Difficulty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TourLogPersistanceTest {

    @Autowired
    private TourLogRepository tourLogRepository;

    @Test
    void testTourLogRepository() {
        // Arrange
        TourLogEntity tourLogEntity = TourLogEntity.builder()
                .comment("super")
                .difficulty(Difficulty.EASY)
                .build();

        // Act
        TourLogEntity control = tourLogRepository.save(tourLogEntity);

        // Assert
        assertEquals(tourLogEntity, control);
    }
}
