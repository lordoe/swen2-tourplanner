package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.service.MapService;
import at.fhtw.swen2.tutorial.service.utils.MapData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MapServiceImplTest {

    @Autowired
    private MapService mapService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testMapMapData() throws JsonProcessingException {
        // Arrange
        MapData mapData = new MapData();
        mapData.setDistance(100.0);
        mapData.setDuration(200.0);
        mapData.setImagePath(null);

        // Act
        String json = objectMapper.writeValueAsString(mapData);
        MapData control = objectMapper.readValue(json, MapData.class);

        // Assert
        assertEquals(mapData.getDistance(), control.getDistance());
        assertEquals(mapData.getDuration(), control.getDuration());
        assertEquals(mapData.getImagePath(), control.getImagePath());
    }
    @Test
    void testGetMapSuccessfully() {
        // Arrange
        String from = "Ulm";
        String to = "Ulm";
        String transportType = "bicycle";

        // Act
        MapData mapData = mapService.getMap(from, to, transportType);

        // Assert
        assertNotNull(mapData);
        System.out.println(mapData.getDistance());
        System.out.println(mapData.getDuration());
    }

    @Test
    void testGetMapUnsuccessfully() {
        // Arrange
        String from = "a";
        String to = "";
        String transportType = "bicycle";

        // Act
        MapData mapData = mapService.getMap(from, to, transportType);

        // Assert
        assertNull(mapData);
    }
}