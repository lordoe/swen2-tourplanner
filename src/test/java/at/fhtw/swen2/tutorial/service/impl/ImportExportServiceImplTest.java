package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.service.MapService;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.utils.MapData;
import at.fhtw.swen2.tutorial.service.utils.TourData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ImportExportServiceImplTest {

    @InjectMocks
    private ImportExportServiceImpl importExportService;

    @Mock
    private TourService tourService;
    @Mock
    private TourLogService tourLogService;
    @Mock
    private MapService mapService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    static void tearDown() throws Exception {
        String filePath = Paths.get("").toAbsolutePath().toString();
        filePath += "/src/test/java/at/fhtw/swen2/tutorial/service/impl/importExport/tour.json";
        Files.deleteIfExists(Paths.get(filePath));
    }

    @Test
    void exportTourData() throws Exception {
        // Arrange
        Tour tour = new Tour();
        tour.setId(1L);

        TourLog tourLog = new TourLog();
        tourLog.setId(1L);
        tourLog.setTourId(tour.getId());
        List<TourLog> tourLogs = Collections.singletonList(tourLog);

        when(tourLogService.findByTourId(tour.getId())).thenReturn(tourLogs);

        String filePath = Paths.get("").toAbsolutePath().toString();
        filePath += "/src/test/java/at/fhtw/swen2/tutorial/service/impl/importExport/tour.json";

        // Act
        importExportService.exportTourData(tour, filePath);

        // Assert
        verify(tourLogService).findByTourId(tour.getId());
        String tourJson = Files.readString(Paths.get(filePath));
        TourData tourData = objectMapper.readValue(tourJson, TourData.class);
        assertEquals(tour, tourData.getTour());
        assertEquals(tourLogs, tourData.getTourLogs());
    }

    @Test
    void importTourData() throws Exception {
        // Arrange
        Tour tour = Tour.builder()
                .id(1L)
                .build();
        TourData tourData = new TourData(tour, Collections.emptyList());

        when(tourService.addNew(any())).thenReturn(tour);
        when(mapService.getMap(any(), any(), any())).thenReturn(new MapData());
        String filePath = Paths.get("").toAbsolutePath().toString();
        filePath += "/src/test/java/at/fhtw/swen2/tutorial/service/impl/importExport/import_test.json";
        objectMapper.writeValue(new java.io.File(filePath), tourData);

        // Act
        TourData result = importExportService.importTourData(filePath);

        // Assert
        assertEquals(tourData, result);
        verify(tourService).addNew(any());
    }
}
