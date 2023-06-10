package at.fhtw.swen2.tutorial.presentation.view;


import at.fhtw.swen2.tutorial.presentation.ViewManager;
import at.fhtw.swen2.tutorial.presentation.utils.AlertRaiser;
import at.fhtw.swen2.tutorial.presentation.viewmodel.SearchTourLogViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Scope("prototype")
@Slf4j
public class ManageTourLogView {

    public static final int PAGE_ITEMS_COUNT = 10;

    @Autowired
    private ViewManager viewManager;
    @Autowired
    private SearchTourLogViewModel searchTourLogViewModel;
    @Autowired
    private TourListViewModel tourListViewModel;
    @Autowired
    private TourLogListViewModel tourLogListViewModel;

    @Autowired
    private AlertRaiser alertRaiser;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Label searchLabel;
    @FXML
    public Label selectedTourLog;
    @FXML
    public Button addTourLogButton;
    @FXML
    public Button deleteTourLogButton;
    @FXML
    public Button editTourLogButton;
    @FXML
    public Button showAllTourLogsButton;
    @FXML
    private void initialize() {

        searchField.textProperty().bindBidirectional(searchTourLogViewModel.searchStringProperty());
        selectedTourLog.textProperty().bind(tourLogListViewModel.selectedTourLogNameProperty());

        // search panel
        searchButton.setOnAction(event -> loadData());

        // buttons
        addTourLogButton.setOnAction(this::addTourLogButtonAction);
        deleteTourLogButton.setOnAction(this::deleteTourLogButtonAction);
        editTourLogButton.setOnAction(this::editTourLogButtonAction);
        showAllTourLogsButton.setOnAction(this::showAllTourLogsButtonAction);

        searchButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
        searchField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                loadData();
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchLabel.setText(newValue);
        });
    }

    private void loadData() {
        searchTourLogViewModel.search();
    }

    public void addTourLogButtonAction(ActionEvent actionEvent) {
        if(tourListViewModel.getSelected() == null){
            alertRaiser.showErrorAlert("No Tour selected","Error", "Please select a Tour to add a TourLog");
            return;
        }
        try {
            tourLogListViewModel.unselect();
            Stage stage = new Stage();
            Parent root1 = viewManager.load("at/fhtw/swen2/tutorial/presentation/view/AddTourLogWindow.fxml", stage);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTourLogButtonAction(ActionEvent actionEvent) {
        if(tourLogListViewModel.getSelected() == null){
            alertRaiser.showErrorAlert("No TourLog selected","Error", "Please select a TourLog to delete");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("You are about to delete Log: " + tourLogListViewModel.getSelected().getId());
        alert.setContentText("Cannot be undone. Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            tourLogListViewModel.deleteSelectedTourLog();
        }
    }

    public void editTourLogButtonAction(ActionEvent actionEvent) {
        if(tourLogListViewModel.getSelected() == null){
            alertRaiser.showErrorAlert("No TourLog selected","Error", "Please select a TourLog to edit");
            return;
        }
        try {
            Stage stage = new Stage();
            Parent root1 = viewManager.load("at/fhtw/swen2/tutorial/presentation/view/AddTourLogWindow.fxml", stage);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void showAllTourLogsButtonAction(ActionEvent actionEvent) {
        tourLogListViewModel.clearTourIdFilter();
    }
}
