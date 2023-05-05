package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.viewmodel.AddTourWindowViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourDetailsViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.service.MapService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.impl.MapServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TourDetailsView {

    @Autowired
    private TourDetailsViewModel tourDetailsViewModel;
    @Autowired
    private MapService mapService;

    public javafx.scene.control.Label titleLabel;

    @FXML
    private TextField tourNameTextField;

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ChoiceBox transportModeCheckBox;

    @FXML
    private Button confirmTourButton;

    @FXML
    private Button cancelTourButton;
    @FXML
    public Button editTourButton;
    @FXML
    public Button showRouteButton;
    @FXML
    public Image image;
    @FXML
    public ImageView imageView;

    private void disableFields() {
        tourNameTextField.setDisable(true);
        tourNameTextField.setStyle("-fx-opacity: 1.0;-fx-background-color: #eaeaea;");
        fromTextField.setDisable(true);
        fromTextField.setStyle("-fx-opacity: 1.0;-fx-background-color: #eaeaea;");
        toTextField.setDisable(true);
        toTextField.setStyle("-fx-opacity: 1.0;-fx-background-color: #eaeaea;");
        descriptionTextField.setDisable(true);
        descriptionTextField.setStyle("-fx-opacity: 1.0;-fx-background-color: #eaeaea;");

        transportModeCheckBox.setDisable(true);
    }

    private void enableFields(ActionEvent actionEvent) {
        tourNameTextField.setDisable(false);
        tourNameTextField.setStyle("-fx-background-color: #ffffff;");
        fromTextField.setDisable(false);
        fromTextField.setStyle("-fx-background-color: #ffffff;");
        toTextField.setDisable(false);
        toTextField.setStyle("-fx-background-color: #ffffff;");
        descriptionTextField.setDisable(false);
        descriptionTextField.setStyle("-fx-background-color: #ffffff;");
        transportModeCheckBox.setDisable(false);
    }
    public void initialize() {

        tourDetailsViewModel.init();

        // Bindings
        tourNameTextField.textProperty().bindBidirectional(tourDetailsViewModel.nameStringProperty());
        fromTextField.textProperty().bindBidirectional(tourDetailsViewModel.fromStringProperty());
        toTextField.textProperty().bindBidirectional(tourDetailsViewModel.toStringProperty());
        descriptionTextField.textProperty().bindBidirectional(tourDetailsViewModel.descriptionStringProperty());

        imageView.imageProperty().bindBidirectional(tourDetailsViewModel.imageProperty());

        // Buttons
        editTourButton.setOnAction(this::enableFields);
        cancelTourButton.setOnAction(this::cancelTourButtonAction);
        confirmTourButton.setOnAction(this::confirmTourButtonAction);

        disableFields();
    }

    public void cancelTourButtonAction(ActionEvent event){
        Stage stage = (Stage) cancelTourButton.getScene().getWindow();
        stage.close();
    }

    public void confirmTourButtonAction(ActionEvent event) {
        Tour updated = tourDetailsViewModel.updateTour();
        if (updated != null) {
            disableFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Tour could not be updated");
            alert.setContentText("Please check your input");
            alert.showAndWait();
        }
    }
}
