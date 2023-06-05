package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.utils.InvalidParamException;
import at.fhtw.swen2.tutorial.presentation.utils.TransportType;
import at.fhtw.swen2.tutorial.presentation.viewmodel.AddTourWindowViewModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;


@Component
@Scope("prototype")
public class AddTourWindowView implements Initializable {

    public javafx.scene.control.Label titleLabel;

    @Autowired
    private AddTourWindowViewModel addTourWindowViewModel;

    @FXML
    private TextField tourNameTextField;

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ChoiceBox<TransportType> transportModeCheckBox;

    @FXML
    private Button confirmTourButton;

    @FXML
    private Button cancelTourButton;

    public AddTourWindowView(AddTourWindowViewModel addTourWindowViewModel) {
        this.addTourWindowViewModel = addTourWindowViewModel;
    }

    public void initialize(URL location, ResourceBundle rb) {
        addTourWindowViewModel.init();

        transportModeCheckBox.getItems().addAll(TransportType.values());

        tourNameTextField.textProperty().bindBidirectional(addTourWindowViewModel.nameStringProperty());
        fromTextField.textProperty().bindBidirectional(addTourWindowViewModel.fromStringProperty());
        toTextField.textProperty().bindBidirectional(addTourWindowViewModel.toStringProperty());
        descriptionTextField.textProperty().bindBidirectional(addTourWindowViewModel.descriptionStringProperty());
        transportModeCheckBox.valueProperty().bindBidirectional(addTourWindowViewModel.transportTypeProperty());
    }

    public void confirmTourButtonAction(ActionEvent event) {
        try {
            // Create a ProgressIndicator for the loading animation
            ProgressIndicator loadingIndicator = new ProgressIndicator();

            // Disable the confirmTourButton while the loading animation is active
            confirmTourButton.setDisable(true);

            AnchorPane root = (AnchorPane) confirmTourButton.getScene().getRoot();
            root.getChildren().add(loadingIndicator);
            AnchorPane.setTopAnchor(loadingIndicator, 0.0);
            AnchorPane.setBottomAnchor(loadingIndicator, 0.0);
            AnchorPane.setLeftAnchor(loadingIndicator, 0.0);
            AnchorPane.setRightAnchor(loadingIndicator, 0.0);


            // Start the loading animation
            loadingIndicator.setProgress(-1); // Indefinite progress

            // Execute the addTour operation asynchronously
            Task<Void> addTourTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    addTourWindowViewModel.addTour();
                    return null;
                }
            };

            // Set up completion handling after the addTour operation is finished
            addTourTask.setOnSucceeded(taskEvent -> {
                // Remove the loadingIndicator from the scene
                root.getChildren().remove(loadingIndicator);

                // Enable the confirmTourButton again
                confirmTourButton.setDisable(false);

                // Close the stage
                Stage stage = (Stage) confirmTourButton.getScene().getWindow();
                stage.close();
            });

            // Set up exception handling if an InvalidParamException is thrown
            addTourTask.setOnFailed(taskEvent -> {
                // Remove the loadingIndicator from the scene
                root.getChildren().remove(loadingIndicator);

                // Enable the confirmTourButton again
                confirmTourButton.setDisable(false);

                // Show an error alert with the exception message
                Throwable exception = addTourTask.getException();
                if (exception instanceof InvalidParamException) {
                    InvalidParamException invalidParamException = (InvalidParamException) exception;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Tour");
                    alert.setContentText(invalidParamException.getMessage());
                    alert.showAndWait();
                }
            });

            // Execute the addTourTask in a separate thread
            Thread addTourThread = new Thread(addTourTask);
            addTourThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void cancelTourButtonAction(ActionEvent event) throws Exception {
        Stage stage = (Stage) cancelTourButton.getScene().getWindow();
        stage.close();
    }

}
