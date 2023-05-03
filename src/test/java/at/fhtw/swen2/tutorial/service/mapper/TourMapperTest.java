package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.persistence.entities.TourLogEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourMapperTest {

    @Autowired
    private TourMapper tourMapper;

    @Test
    void fromEntity() {
        // Arrange
        List<TourLogEntity> tourLogEntities = new ArrayList<>();
        TourEntity tourEntity = TourEntity.builder().id(1L).name("test").build();
        tourLogEntities.add(TourLogEntity.builder().id(1L).comment("super").tour(tourEntity).build());
        tourLogEntities.add(TourLogEntity.builder().id(2L).comment("faad").tour(tourEntity).build());
        tourEntity.setTourLogs(tourLogEntities);

        // Act
        Tour tour = tourMapper.fromEntity(tourEntity);

        // Assert
        assertEquals(tourEntity.getId(), tour.getId());
        assertEquals(tourEntity.getName(), tour.getName());

    }

    @Test
    void toEntity() {
        // Arrange
        List<TourLog> tourLogs = new ArrayList<>();
        Tour tour = Tour.builder().id(1L).name("test").build();

        // Act
        TourEntity tourEntity = tourMapper.toEntity(tour);

        // Assert
        assertEquals(tour.getId(), tourEntity.getId());
        assertEquals(tour.getName(), tourEntity.getName());
        //System.out.println(tourEntity.getTourLogs());
    }
}