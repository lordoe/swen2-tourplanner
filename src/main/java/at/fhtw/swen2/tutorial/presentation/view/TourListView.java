package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class TourListView implements Initializable {

    @Autowired
    private TourListViewModel tourListViewModel;
    @Autowired
    private TourLogListViewModel tourLogListViewModel;
    @FXML
    public TableView tableView = new TableView<>();
    @FXML
    private VBox dataContainer;

    @Override
    public void initialize(URL location, ResourceBundle rb){
        tableView.setItems(tourListViewModel.getTourListItems());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory("id"));
        id.setMinWidth(20);
        id.setMaxWidth(50);
        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory("name"));
        tableView.getColumns().addAll(id, name);

        dataContainer.getChildren().add(tableView);
        tourListViewModel.initList();

        // add onclick event
        tableView.setRowFactory(tv -> {
            TableRow<Tour> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(! row.isEmpty()){
                    Tour rowData = row.getItem();
                    if(event.getClickCount() == 2){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("TOUR INFO");
                        alert.setHeaderText("Info about Tour");
                        alert.setContentText(rowData.toString());
                        alert.showAndWait();
                    }
                    // select tour for further processing
                    tourListViewModel.select(rowData);
                    // show tourLogs
                    tourLogListViewModel.showLogsOfTour(rowData);
                }
            });
            return row;
        });
    }

}
