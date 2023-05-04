package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.ViewManager;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    @Autowired
    private ViewManager viewManager;
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
                    if(event.getClickCount() == 2 && tourListViewModel.getSelected() != null){
                        try {
                            Stage stage = new Stage();
                            Parent root1 = viewManager.load("at/fhtw/swen2/tutorial/presentation/view/TourDetailsWindow.fxml", stage);
                            stage.setScene(new Scene(root1));
                            stage.show();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
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
