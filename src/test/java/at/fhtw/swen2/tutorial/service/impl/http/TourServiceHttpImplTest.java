package at.fhtw.swen2.tutorial.service.impl.http;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
    TEST NOTE:
    tests backend connection
    for test to work the http server must be running
    and the http service must be primary
 */
@SpringBootTest
class TourServiceHttpImplTest {

    @Autowired
    TourServiceHttpImpl tourServiceHttp;

    @Test
    void testAddNew() {
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
        Tour control = tourServiceHttp.addNew(tour);

        // Assert
        assertNotNull(control);
        assertEquals(tour.getName(), control.getName());
        assertNotNull(control.getId());
    }

    @Test
    void testFindById_delete() {
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
        Tour control = tourServiceHttp.addNew(tour);
        // test findById
        Tour find = tourServiceHttp.findById(control.getId());
        // test delete
        tourServiceHttp.delete(find);
        Tour notFound = tourServiceHttp.findById(find.getId());

        // Assert
        assertNotNull(find);
        assertEquals(control.getId(), find.getId());
        assertNull(notFound);
    }

    @Test
    void testUpdate() {
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
        Tour control = tourServiceHttp.addNew(tour);
        control.setName("Changed");
        Tour updated = tourServiceHttp.update(control);

        // Assert
        assertNotNull(updated);
        assertEquals("Changed", updated.getName());

        // cleanup
        tourServiceHttp.delete(updated);
    }

    @Test
    void testGetList(){
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
        Tour tour2 = Tour.builder()
                .name("Wanderung2")
                .description("langer Hatscher2")
                .from("Scheibs2")
                .to("Nebraska2")
                .distance(33.)
                .estimatedTime(33.)
                .transportType("foot")
                .routeInformation("link")
                .build();

        // Act
        tourServiceHttp.addNew(tour);
        tourServiceHttp.addNew(tour2);
        List<Tour> tours = tourServiceHttp.getList();

        // Assert
        assertNotNull(tours);
        System.out.println(tours);

        // cleanup
        tourServiceHttp.delete(tour);
        tourServiceHttp.delete(tour2);
    }
}