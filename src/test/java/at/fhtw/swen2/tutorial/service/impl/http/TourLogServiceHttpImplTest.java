package at.fhtw.swen2.tutorial.service.impl.http;

import at.fhtw.swen2.tutorial.service.Service;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/*
    TEST NOTE:
    integration test
    tests backend connection
    for test to work the http server must be running
    and the http service must be primary
 */
@SpringBootTest
class TourLogServiceHttpImplTest {

    @Autowired
    private TourLogServiceHttpImpl tourLogServiceHttp;
    @Autowired
    private Service<Tour> tourService;

    @Test
    void getList() {
        // Arrange
        Tour tour = Tour.builder()
                .name("Wanderung")
                .description("langer Hatscher")
                .build();
        Tour added = tourService.addNew(tour);
        TourLog tourLog = TourLog.builder()
                .comment("Wanderung")
                .TourId(added.getId())
                .build();
        TourLog tourLog1 = TourLog.builder()
                .comment("Hatscher")
                .TourId(added.getId())
                .build();
        tourLogServiceHttp.addNew(tourLog);
        tourLogServiceHttp.addNew(tourLog1);

        // Act
        List<TourLog> tourLogs = tourLogServiceHttp.getList();

        // Assert
        assertNotNull(tourLogs);
        System.out.println(tourLogs);

        // Clean up
        tourLogServiceHttp.delete(tourLog);
        tourLogServiceHttp.delete(tourLog1);
    }

    @Test
    void addNew() {
        // Arrange
        Tour tour = Tour.builder()
                .name("Wanderung")
                .description("langer Hatscher")
                .build();
        Tour added = tourService.addNew(tour);
        TourLog tourLog = TourLog.builder()
                .comment("Wanderung")
                .TourId(added.getId())
                .build();

        // Act
        TourLog control = tourLogServiceHttp.addNew(tourLog);

        // Assert
        assertNotNull(control);
        assertEquals(tourLog.getComment(), control.getComment());

        // Clean up
        tourLogServiceHttp.delete(control);
    }

    @Test
    void findById() {
        // Arrange
        Tour tour = Tour.builder()
                .name("Wanderung")
                .description("langer Hatscher")
                .build();
        Tour added = tourService.addNew(tour);
        TourLog tourLog = TourLog.builder()
                .comment("Wanderung")
                .TourId(added.getId())
                .build();
        TourLog control = tourLogServiceHttp.addNew(tourLog);

        // Act
        TourLog tourLog1 = tourLogServiceHttp.findById(control.getId());

        // Assert
        assertNotNull(tourLog1);
        assertEquals(control.getId(), tourLog1.getId());

        // Clean up
        tourLogServiceHttp.delete(control);
        tourService.delete(added);
    }

    @Test
    void delete() {
        // Arrange
        Tour tour = Tour.builder()
                .name("Wanderung")
                .description("langer Hatscher")
                .build();
        Tour added = tourService.addNew(tour);
        TourLog tourLog = TourLog.builder()
                .comment("Wanderung")
                .TourId(added.getId())
                .build();
        TourLog control = tourLogServiceHttp.addNew(tourLog);

        // Act
        tourLogServiceHttp.delete(control);
        TourLog tourLog1 = tourLogServiceHttp.findById(control.getId());

        // Assert
        assertNull(tourLog1);

        // Clean up
        tourService.delete(added);
    }

    @Test
    void update() {
        // Arrange
        Tour tour = Tour.builder()
                .name("Wanderung")
                .description("langer Hatscher")
                .build();
        Tour added = tourService.addNew(tour);
        TourLog tourLog = TourLog.builder()
                .comment("Wanderung")
                .TourId(added.getId())
                .build();
        TourLog control = tourLogServiceHttp.addNew(tourLog);
        control.setComment("Hatscher");

        // Act
        TourLog tourLog1 = tourLogServiceHttp.update(control);

        // Assert
        assertNotNull(tourLog1);
        assertEquals(control.getComment(), tourLog1.getComment());

        // Clean up
        tourLogServiceHttp.delete(control);
        tourService.delete(added);
    }

    @Test
    void findByTourId() {
        // Arrange
        Tour tour = Tour.builder()
                .name("Wanderung")
                .description("langer Hatscher")
                .build();
        Tour added = tourService.addNew(tour);
        TourLog tourLog = TourLog.builder()
                .comment("Wanderung")
                .TourId(added.getId())
                .build();
        TourLog tourLog1 = TourLog.builder()
                .comment("Hatscher")
                .TourId(added.getId())
                .build();
        tourLogServiceHttp.addNew(tourLog);
        tourLogServiceHttp.addNew(tourLog1);

        // Act
        List<TourLog> tourLogs = tourLogServiceHttp.findByTourId(added.getId());

        // Assert
        assertNotNull(tourLogs);
        assertEquals(2, tourLogs.size());
        System.out.println(tourLogs);

        // Clean up
        tourService.delete(added);
    }
}