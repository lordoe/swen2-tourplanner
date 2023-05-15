package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TourLogListViewModel {

    @Autowired
    private TourLogService tourLogService;

    private Long tourIdFilter; // null = no filter, else filter in search by tourId
    private TourLog selected;
    private final SimpleStringProperty selectedTourLogName = new SimpleStringProperty();
    private final List<TourLog> masterData = new ArrayList<>();
    private final ObservableList<TourLog> tourLogListItems = FXCollections.observableArrayList();

    public ObservableList<TourLog> getTourLogListListItems() {
        return tourLogListItems;
    }

    public void addItem(TourLog tourLog) {
        masterData.add(tourLog);
        tourLogListItems.add(tourLog);
    }

    public void clearItems(){
        masterData.clear();
        tourLogListItems.clear();
    }

    public void initList(){
        tourLogService.getList().forEach(this::addItem);
    }

    public void setTourIdFilter(Tour tour) {
        tourIdFilter = tour.getId();
        filterList("");
    }

    public void clearTourIdFilter() {
        tourIdFilter = null;
        filterList("");
    }
    public void filterList(String searchText){
        Task<List<TourLog>> task = new Task<>() {
            @Override
            protected List<TourLog> call() throws Exception {
                updateMessage("Loading data");
                return masterData
                        .stream()
                        .filter(value ->
                                (tourIdFilter == null || value.getTourId().equals(tourIdFilter)) &&
                                    ( value.getComment().toLowerCase().contains(searchText.toLowerCase())
                                    || value.getId().toString().contains(searchText)
                                    || value.getDifficulty().difficulty.contains(searchText) )
                        )
                        .collect(Collectors.toList());
            }
        };

        task.setOnSucceeded(event -> {
            tourLogListItems.setAll(task.getValue());
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }


    public void removeLogsOfTour(Tour selected) {
        tourLogListItems.removeIf(tourLog -> tourLog.getTourId().equals(selected.getId()));
        masterData.removeIf(tourLog -> tourLog.getTourId().equals(selected.getId()));
    }

    public void select(TourLog rowData) {
        this.selected = rowData;
        selectedTourLogName.set("LOG: " + rowData.getId().toString());
    }

    public void deleteSelectedTourLog(){
        tourLogService.delete(selected);
        tourLogListItems.remove(selected);
        masterData.remove(selected);
        selected = null;
    }

    public TourLog getSelected() {
        return selected;
    }

    public StringProperty selectedTourLogNameProperty() {
        return selectedTourLogName;
    }

    public void unselect() {
        this.selected = null;
    }
}
