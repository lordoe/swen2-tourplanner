package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class TourListViewModel {
    @Autowired
    public TourService tourService;

    @Autowired
    public TourLogListViewModel tourLogListViewModel;

    private final ObservableList<Tour> tourListItems = FXCollections.observableArrayList();

    private Tour selected;
    private final SimpleStringProperty selectedTourName = new SimpleStringProperty();
    private final List<Tour> masterData = new ArrayList<>();
    private final SimpleStringProperty searchString = new SimpleStringProperty();

    public void select(Tour rowData) {
        this.selected = rowData;
        setSelectedTourName(" " + rowData.getName());
        System.out.println(selectedTourName);
    }

    public void setSelectedTourName(String selectedTourName) {
        this.selectedTourName.set(selectedTourName);
    }

    public String getSelectedTourName() {
        return selectedTourName.get();
    }

    public StringProperty selectedTourNameProperty(){
        return selectedTourName;
    }
    public void initList() {
        tourService.getList().forEach(this::addItem);
    }
    public ObservableList<Tour> getTourListItems() { return tourListItems; }

    public void addItem(Tour tour){
        tourListItems.add(tour);
        masterData.add(tour);
    }

    public Tour getSelected() {
        return selected;
    }

    public void deleteSelectedTour(){
        tourService.delete(selected);
        tourListItems.remove(selected);
        masterData.remove(selected);
        tourLogListViewModel.removeLogsOfTour(selected);
        tourLogListViewModel.filterList("");
        setSelectedTourName(" deleted " + selected.getName());
        selected = null;
    }

    // Search
    public String getSearchString() {
        return searchString.get();
    }

    public SimpleStringProperty searchStringProperty() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString.set(searchString);
    }

    public void search(){
        filterList(getSearchString());
    }

    public void filterList(String searchText){
        Task<List<Tour>> task = new Task<>() {
            @Override
            protected List<Tour> call() throws Exception {
                updateMessage("Loading data");
                return masterData
                        .stream()
                        .filter(value -> value.getName().toLowerCase().contains(searchText.toLowerCase())
                        || value.getId().toString().contains(searchText.toLowerCase()))
                        .collect(Collectors.toList());
            }
        };

        task.setOnSucceeded(event -> {
            tourListItems.setAll(task.getValue());
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
}
