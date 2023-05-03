package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.persistence.utils.Difficulty;
import at.fhtw.swen2.tutorial.presentation.viewmodel.AddTourLogWindowViewModel;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

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

    public void initialize() {
        confirmButton.setOnAction(this::confirmButtonAction);
        cancelButton.setOnAction(this::cancelButtonAction);
        difficultySelection.getItems().addAll(Difficulty.values());
        hourInput.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12);
        minInput.getItems().addAll(0,5,10,15,20,25,30,35,40,45,50,55);
        ratingSlider.minProperty().setValue(1);
        ratingSlider.maxProperty().setValue(10);
    }

    private Boolean allValuesSet() {
        return dateSelection.getValue() != null
                && difficultySelection.getValue() != null
                && hourInput.getValue() != null
                && minInput.getValue() != null
                && !commentTextField.getText().isEmpty();
    }

    public void confirmButtonAction(ActionEvent actionEvent) {
        if(!allValuesSet()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not all values set");
            alert.setContentText("Please fill out all fields");
            alert.showAndWait();
            return;
        }
        TourLog tourLog = TourLog.builder()
                .comment(commentTextField.getText())
                .dateTime(Date.from(dateSelection.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .difficulty(difficultySelection.getValue())
                .rating(Double.valueOf(ratingSlider.getValue()).intValue())
                .totalTime(hourInput.getValue() * 60 + minInput.getValue())
                .build();

        addTourLogWindowViewModel.addTourLog(tourLog);

        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    public void cancelButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
