package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.persistence.utils.Difficulty;
import at.fhtw.swen2.tutorial.presentation.utils.InvalidParamException;
import at.fhtw.swen2.tutorial.presentation.viewmodel.AddTourLogWindowViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class AddTourLogWindowView {

    @Autowired
    private AddTourLogWindowViewModel addTourLogWindowViewModel;

    @FXML
    public Button confirmButton;
    @FXML
    public TextField commentTextField;
    @FXML
    public Button cancelButton;
    @FXML
    public ChoiceBox<Difficulty> difficultySelection;
    @FXML
    public DatePicker dateSelection;
    @FXML
    public Slider ratingSlider;
    @FXML
    public ChoiceBox<Integer> hourInput;
    @FXML
    public ChoiceBox<Integer> minInput;
    @FXML
    public Label tourName;

    public void initialize() {
        // init view model
        addTourLogWindowViewModel.init();

        // add event handlers
        confirmButton.setOnAction(this::confirmButtonAction);
        cancelButton.setOnAction(this::cancelButtonAction);

        // init choice boxes
        difficultySelection.getItems().addAll(Difficulty.values());
        hourInput.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12);
        minInput.getItems().addAll(0,5,10,15,20,25,30,35,40,45,50,55);
        ratingSlider.minProperty().setValue(1);
        ratingSlider.maxProperty().setValue(10);

        // bind properties
        commentTextField.textProperty().bindBidirectional(addTourLogWindowViewModel.commentProperty());
        hourInput.valueProperty().bindBidirectional(addTourLogWindowViewModel.hourProperty());
        minInput.valueProperty().bindBidirectional(addTourLogWindowViewModel.minuteProperty());
        difficultySelection.valueProperty().bindBidirectional(addTourLogWindowViewModel.difficultyProperty());
        ratingSlider.valueProperty().bindBidirectional(addTourLogWindowViewModel.ratingProperty());
        dateSelection.valueProperty().bindBidirectional(addTourLogWindowViewModel.dateProperty());

        // set values
        tourName.textProperty().setValue(addTourLogWindowViewModel.tourIDProperty());
    }

    public void confirmButtonAction(ActionEvent actionEvent) {
        try {
            confirmButton.setText("loading...");
            addTourLogWindowViewModel.addTourLog();
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        } catch (InvalidParamException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Data");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            confirmButton.setText("save");
        }
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
