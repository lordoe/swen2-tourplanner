package at.fhtw.swen2.tutorial.service.mapper;

import at.fhtw.swen2.tutorial.persistence.entities.TourEntity;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourMapperTest {

    @Autowired
    private TourMapper tourMapper;

    @Test
    void fromEntity() {

    }

    @Test
    void toEntity() {
        // Arrange
        Collection<TourLog> tourLogs = new ArrayList<>();
        tourLogs.add(TourLog.builder().id(1L).comment("super").build());
        tourLogs.add(TourLog.builder().id(2L).comment("faad").build());
        Tour tour = Tour.builder().id(1L).name("test").tourLogs(tourLogs).build();

        // Act
        TourEntity tourEntity = tourMapper.toEntity(tour);

        // Assert
        assertEquals(tour.getId(), tourEntity.getId());
        assertEquals(tour.getName(), tourEntity.getName());
        assertEquals(tour.getTourLogs().size(), tourEntity.getTourLogs().size());
        assertEquals(tour.getTourLogs().iterator().next().getId(), tourEntity.getTourLogs().iterator().next().getId());
    }
}