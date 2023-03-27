package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourListViewModel {
    @Autowired
    public TourService tourService;

    private final ObservableList<Tour> tourListItems = FXCollections.observableArrayList();

    private Tour selected;
    private SimpleStringProperty selectedTourName = new SimpleStringProperty();

    public void select(Tour rowData) {
        this.selected = rowData;
        this.selectedTourName = new SimpleStringProperty(rowData.getName());
        System.out.println(selectedTourName);
    }

    public StringProperty selectedTourNameProperty(){
        return selectedTourName;
    }
    public void initList() {
        tourService.getList().forEach(tour -> {
            addItem(tour);
        });
    }
    public ObservableList<Tour> getTourListItems() { return tourListItems; }

    public void addItem(Tour tour){
        tourListItems.add(tour);
    }

    public Tour getSelected() {
        return selected;
    }
}
