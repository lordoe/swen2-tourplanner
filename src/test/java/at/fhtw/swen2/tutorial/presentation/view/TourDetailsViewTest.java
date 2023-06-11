package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.utils.AlertRaiser;
import at.fhtw.swen2.tutorial.presentation.utils.InvalidParamException;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourDetailsViewModel;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
class TourDetailsViewTest {

    @Autowired
    private TourDetailsView tourDetailsView;

    @MockBean
    private TourDetailsViewModel tourDetailsViewModel;
    @MockBean
    private AlertRaiser alertRaiser;

    @Test
    void testConfirmTourButtonAction_validInput() throws InvalidParamException {
        // Arrange
        tourDetailsView = spy(tourDetailsView);                 // to mock disableFields()
        doNothing().when(tourDetailsViewModel).updateTour();
        doNothing().when(tourDetailsView).disableFields();


        // Act
        tourDetailsView.confirmTourButtonAction(mock(ActionEvent.class));

        // Assert
        verify(tourDetailsViewModel).updateTour();
        verify(alertRaiser, never()).showErrorAlert(any(), any(), any());
    }

    @Test
    void testConfirmTourButtonAction_invalidInput() throws InvalidParamException {
        // Arrange
        String errorMessage = "Invalid Param";
        doThrow(new InvalidParamException(errorMessage)).when(tourDetailsViewModel).updateTour();

        // Act
        tourDetailsView.confirmTourButtonAction(mock(ActionEvent.class));

        // Assert
        verify(tourDetailsViewModel).updateTour();
        verify(alertRaiser).showErrorAlert("Error Dialog", "Error", errorMessage);
    }
}
