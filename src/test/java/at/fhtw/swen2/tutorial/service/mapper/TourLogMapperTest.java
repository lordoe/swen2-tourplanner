package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.persistence.utils.Difficulty;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TourLogMapperTest {

    @Autowired
    private TourLogMapper tourLogMapper;

    @Autowired
    private TourService tourService;

    @Test
    void tourLogFromEntityTest() {
        // Arrange
        TourEntity tourEntity = TourEntity.builder().name("hello world").build();
        TourLogEntity tourLogEntity = TourLogEntity.builder()
                .id(7L)
                .rating(19)
                .totalTime(22)
                .difficulty(Difficulty.EASY)
                .dateTime(new Date(333))
                .comment("testcase")
                .tour(tourEntity)
                .build();
        tourEntity.addTourLog(tourLogEntity);

        // Act
        TourLog tourLog = tourLogMapper.fromEntity(tourLogEntity);

        // Assert
        assertEquals(tourLogEntity.getComment(), tourLog.getComment());
        assertEquals(tourLogEntity.getId(),tourLog.getId());
        assertEquals(tourLogEntity.getRating(), tourLog.getRating());
        assertEquals(tourLogEntity.getTotalTime(), tourLog.getTimeInMinutes());
        assertEquals(tourLogEntity.getDifficulty(), tourLog.getDifficulty());
        assertEquals(tourLogEntity.getTour().getId(), tourLog.getTourId());
    }

    @Test
    void tourLogToEntityTest() {
        // Arrange
        Tour tour = tourService.addNew(Tour.builder().name("myTour").build());
        TourLog tourLog = TourLog.builder()
                .id(7L)
                .rating(19)
                .timeInMinutes(22)
                .difficulty(Difficulty.EASY)
                .dateTime(new Date(333))
                .comment("testcase")
                .TourId(tour.getId())
                .build();

        // Act
        TourLogEntity tourLogEntity = tourLogMapper.toEntity(tourLog);

        // Assert
        assertEquals(tourLogEntity.getComment(), tourLog.getComment());
        assertEquals(tourLogEntity.getId(),tourLog.getId());
        assertEquals(tourLogEntity.getRating(), tourLog.getRating());
        assertEquals(tourLogEntity.getTotalTime(), tourLog.getTimeInMinutes());
        assertEquals(tourLogEntity.getDifficulty(), tourLog.getDifficulty());
        assertEquals(tourLogEntity.getTour().getId(), tourLog.getTourId());
        System.out.println(tourLogEntity);
    }
}