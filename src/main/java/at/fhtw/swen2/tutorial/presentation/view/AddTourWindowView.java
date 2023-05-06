package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.utils.InvalidParamException;
import at.fhtw.swen2.tutorial.presentation.viewmodel.AddTourWindowViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;



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
    private ChoiceBox transportModeCheckBox;

    @FXML
    private Button confirmTourButton;

    @FXML
    private Button cancelTourButton;

    public AddTourWindowView(AddTourWindowViewModel addTourWindowViewModel) {
        this.addTourWindowViewModel = addTourWindowViewModel;
    }

    public void initialize(URL location, ResourceBundle rb) {
        addTourWindowViewModel.init();
        tourNameTextField.textProperty().bindBidirectional(addTourWindowViewModel.nameStringProperty());
        fromTextField.textProperty().bindBidirectional(addTourWindowViewModel.fromStringProperty());
        toTextField.textProperty().bindBidirectional(addTourWindowViewModel.toStringProperty());
        descriptionTextField.textProperty().bindBidirectional(addTourWindowViewModel.descriptionStringProperty());
    }

    public void confirmTourButtonAction(ActionEvent event) {
        try {
            confirmTourButton.setText("loading...");
            addTourWindowViewModel.addTour();
            Stage stage = (Stage) confirmTourButton.getScene().getWindow();
            stage.close();
        } catch (InvalidParamException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Tour");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            confirmTourButton.setText("save");
        }
    }

    public void cancelTourButtonAction(ActionEvent event){
        Stage stage = (Stage) cancelTourButton.getScene().getWindow();
        stage.close();
    }

}
