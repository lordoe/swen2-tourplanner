package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.ImportExportService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.utils.TourData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ManageTourViewModelTest {

    @Autowired
    private ManageTourViewModel manageTourViewModel;

    @MockBean
    private ImportExportService importExportService;

    @MockBean
    private TourListViewModel tourListViewModel;

    @MockBean
    private TourLogListViewModel tourLogListViewModel;

    @Test
    void testExportTour_noTourSelected() {
        // Arrange
        when(tourListViewModel.getSelected()).thenReturn(null);

        // Act
        manageTourViewModel.exportTour("/path/to/directory");

        // Assert
        verify(importExportService, never()).exportTourData(any(Tour.class), anyString());
    }

    @Test
    void testExportTour_tourSelected() {
        // Arrange
        Tour selectedTour = Tour.builder().id(1L).name("Tour1").build();
        when(tourListViewModel.getSelected()).thenReturn(selectedTour);

        // Act
        manageTourViewModel.exportTour("/path/to/directory");

        // Assert
        verify(importExportService).exportTourData(selectedTour, "/path/to/directory/" + selectedTour.getId() + "_" + selectedTour.getName() + ".json");
    }

    @Test
    void testImportTour_tourDataNotNull() throws IOException {
        // Arrange
        TourData tourData = TourData.builder().build();
        tourData.setTour(Tour.builder().build());
        List<TourLog> tourLogs = List.of(new TourLog(), new TourLog());
        tourData.setTourLogs(tourLogs);
        when(importExportService.importTourData("/path/to/tourData.json")).thenReturn(tourData);

        // Act
        manageTourViewModel.importTour("/path/to/tourData.json");

        // Assert
        verify(tourListViewModel).addItem(tourData.getTour());
        verify(tourLogListViewModel, times(tourData.getTourLogs().size())).addItem(any());
    }

    @Test
    void testImportTour_tourDataNull() throws IOException {
        // Arrange
        when(importExportService.importTourData("/path/to/tourData.json")).thenReturn(null);

        // Act
        manageTourViewModel.importTour("/path/to/tourData.json");

        // Assert
        verify(tourListViewModel, never()).addItem(any());
        verify(tourLogListViewModel, never()).addItem(any());
    }
}
