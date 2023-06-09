package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.utils.InvalidParamException;
import at.fhtw.swen2.tutorial.presentation.utils.TransportType;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourDetailsViewModel;
import at.fhtw.swen2.tutorial.service.utils.PdfGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TourDetailsView {

    @Autowired
    private TourDetailsViewModel tourDetailsViewModel;

    @Autowired
    private PdfGenerator pdfGeneratorDemo;

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
    private ChoiceBox<TransportType> transportModeCheckBox;

    @FXML
    private Button confirmTourButton;

    @FXML
    private Button cancelTourButton;
    @FXML
    public Button editTourButton;
    @FXML
    public Button exportTourButton;
    @FXML
    public Button reportTourButton;
    @FXML
    public ImageView imageView;
    @FXML
    public Label estimatedTime;
    @FXML
    public Label distance;

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
        transportModeCheckBox.setStyle("-fx-opacity: 1.0;-fx-background-color: #eaeaea;");
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
        transportModeCheckBox.setStyle("-fx-background-color: #ffffff;");
    }
    public void initialize() {

        tourDetailsViewModel.init();

        // Bindings
        tourNameTextField.textProperty().bindBidirectional(tourDetailsViewModel.nameStringProperty());
        fromTextField.textProperty().bindBidirectional(tourDetailsViewModel.fromStringProperty());
        toTextField.textProperty().bindBidirectional(tourDetailsViewModel.toStringProperty());
        descriptionTextField.textProperty().bindBidirectional(tourDetailsViewModel.descriptionStringProperty());
        transportModeCheckBox.valueProperty().bindBidirectional(tourDetailsViewModel.transportTypeProperty());
        imageView.imageProperty().bindBidirectional(tourDetailsViewModel.imageProperty());
        estimatedTime.textProperty().bindBidirectional(tourDetailsViewModel.estimatedTimeProperty());
        distance.textProperty().bindBidirectional(tourDetailsViewModel.distanceProperty());

        transportModeCheckBox.getItems().addAll(TransportType.values());

        // Buttons
        editTourButton.setOnAction(this::enableFields);
        cancelTourButton.setOnAction(this::cancelTourButtonAction);
        confirmTourButton.setOnAction(this::confirmTourButtonAction);
        reportTourButton.setOnAction(this::reportTourButtonAction);

        disableFields();
    }

    public void cancelTourButtonAction(ActionEvent event){
        Stage stage = (Stage) cancelTourButton.getScene().getWindow();
        stage.close();
    }

    public void confirmTourButtonAction(ActionEvent event) {
        try {
            tourDetailsViewModel.updateTour();
            disableFields();
        } catch (InvalidParamException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @SneakyThrows
    public void reportTourButtonAction(ActionEvent event){
        pdfGeneratorDemo.generateTourReport();
    }
}
