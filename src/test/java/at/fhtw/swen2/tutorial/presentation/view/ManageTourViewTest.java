package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.utils.AlertRaiser;
import at.fhtw.swen2.tutorial.presentation.viewmodel.ManageTourViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ManageTourViewTest {

    @MockBean
    private TourListViewModel tourListViewModel;
    @MockBean
    private AlertRaiser alertRaiser;
    @MockBean
    private ManageTourViewModel manageTourViewModel;

    @Autowired
    private ManageTourView manageTourView;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteTourButtonAction_noTourSelected() {
        // Arrange
        when(tourListViewModel.getSelected()).thenReturn(null);

        // Act
        manageTourView.deleteTourButtonAction(null);

        // Assert
        verify(alertRaiser).showErrorAlert("Error", "No Tour selected", "Please select a Tour to delete");
        verify(tourListViewModel, never()).deleteSelectedTour();
    }

    @Test
    void testExportTour_noTourSelected() {
        // Arrange
        when(tourListViewModel.getSelected()).thenReturn(null);

        // Act
        manageTourView.exportButtonAction(null);

        // Assert
        verify(alertRaiser).showErrorAlert("Error", "No Tour selected", "Please select a Tour to export");
        verify(manageTourViewModel, never()).exportTour(anyString());
    }

    @Test
    void testDeleteTourButtonAction_tourSelected() {
        // Arrange
        Tour selectedTour = Tour.builder().id(1L).name("Test").build();
        when(tourListViewModel.getSelected()).thenReturn(selectedTour);

        // Mock confirmation dialog
        when(alertRaiser.showConfirmationAlert(anyString(), anyString(), anyString())).thenReturn(Optional.of(ButtonType.OK));

        // Act
        manageTourView.deleteTourButtonAction(null);

        // Assert
        verify(alertRaiser, never()).showErrorAlert(anyString(), anyString(), anyString());
        verify(tourListViewModel).deleteSelectedTour();
    }

}
