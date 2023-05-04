package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.ViewManager;
import at.fhtw.swen2.tutorial.presentation.viewmodel.AddTourWindowViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
@Slf4j
public class ManageTourView implements Initializable {

    @Autowired
    private TourListViewModel tourListViewModel;

    @Autowired
    private ViewManager viewManager;
    @FXML
    private Button addTourButton;
    @FXML
    private Button deleteTourButton;
    @FXML
    public Button editTourButton;
    @FXML
    private Label selectedTour;
    @FXML
    private Button searchTourButton;
    @FXML
    private TextField searchTourField;
    @FXML
    public Label searchLabel;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        selectedTour.textProperty().bind(tourListViewModel.selectedTourNameProperty());
        System.out.println("initailized");

        searchTourField.textProperty().bindBidirectional(tourListViewModel.searchStringProperty());
        // search panel
        searchTourButton.setOnAction(event -> tourListViewModel.search());
        searchTourButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
        searchTourField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                tourListViewModel.search();
            }
        });
        searchTourField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchLabel.setText(newValue);
        });
    }

    public void addTourButtonAction(ActionEvent event) {
        //feedbackText.setText("Add Tour Button pressed!");
        try {
            Stage stage = new Stage();
            Parent root1 = viewManager.load("at/fhtw/swen2/tutorial/presentation/view/AddTourWindow.fxml", stage);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTourButtonAction(ActionEvent event) {
        if(tourListViewModel.getSelected() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Tour selected");
            alert.setContentText("Please select a Tour to delete");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("You are about to delete " + selectedTour.getText());
        alert.setContentText("Cannot be undone. Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            tourListViewModel.deleteSelectedTour();
        }
    }

    public void editTourButtonAction(ActionEvent actionEvent) {
    }
}
