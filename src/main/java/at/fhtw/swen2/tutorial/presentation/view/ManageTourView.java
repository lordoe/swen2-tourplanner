package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.ViewManager;
import at.fhtw.swen2.tutorial.presentation.viewmodel.ManageTourViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
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
    @Autowired
    private ManageTourViewModel manageTourViewModel;
    @FXML
    private Button addTourButton;
    @FXML
    private Button deleteTourButton;
    @FXML
    public Button tourInfoButton;
    @FXML
    private Label selectedTour;
    @FXML
    private Button searchTourButton;
    @FXML
    private TextField searchTourField;
    @FXML
    public Label searchLabel;
    public Button importTourButton;
    public Button exportTourButton;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        selectedTour.textProperty().bind(tourListViewModel.selectedTourNameProperty());
        System.out.println("initialized");

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

    public void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void deleteTourButtonAction(ActionEvent event) {
        if(tourListViewModel.getSelected() == null){
            showErrorAlert("Error", "No Tour selected", "Please select a Tour to delete");
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

    public void tourInfoButtonAction(ActionEvent actionEvent) {
        if(tourListViewModel.getSelected() == null) {
            showErrorAlert("Error", "No Tour selected", "Please select a Tour to view");
            return;
        }
        try {
            Stage stage = new Stage();
            Parent root1 = viewManager.load("at/fhtw/swen2/tutorial/presentation/view/TourDetailsWindow.fxml", stage);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void importButtonAction(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Tour to import");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(importTourButton.getScene().getWindow());
            if (selectedFile != null) {
                manageTourViewModel.importTour(selectedFile.getAbsolutePath());
            }
        } catch (Exception e) {
            showErrorAlert("Error", "Error while importing Tour", e.getMessage());
        }
    }

    public void exportButtonAction(ActionEvent actionEvent) {
        if(tourListViewModel.getSelected() == null) {
            showErrorAlert("Error", "No Tour selected", "Please select a Tour to export");
            return;
        }
        Stage stage = (Stage) exportTourButton.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select directory to export tour");
        directoryChooser.setInitialDirectory(new java.io.File(System.getProperty("user.home")));
        java.io.File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            manageTourViewModel.exportTour(selectedDirectory.getAbsolutePath());
        }
    }
}
