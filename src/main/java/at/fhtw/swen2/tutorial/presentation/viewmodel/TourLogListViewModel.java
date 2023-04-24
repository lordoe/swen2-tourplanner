package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
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

    private final List<TourLog> masterData = new ArrayList<>();
    private final ObservableList<TourLog> tourLogListItems = FXCollections.observableArrayList();

    public ObservableList<TourLog> gettourLogListListItems() {
        return tourLogListItems;
    }

    public void addItem(TourLog tourLog) {
        tourLogListItems.add(tourLog);
        masterData.add(tourLog);
    }

    public void clearItems(){
        masterData.clear();
        tourLogListItems.clear();
    }

    public void initList(){
        tourLogService.getList().forEach(this::addItem);
    }

    public void showLogsOfTour(Tour tour){
        clearItems();
        List<TourLog> tourLogs = tour.getTourLogs();
        tourLogs.forEach(this::addItem);
    }

    public void filterList(String searchText){
        Task<List<TourLog>> task = new Task<>() {
            @Override
            protected List<TourLog> call() throws Exception {
                updateMessage("Loading data");
                return masterData
                        .stream()
                        .filter(value -> value.getComment().toLowerCase().contains(searchText.toLowerCase()))
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


}
