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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TourLogListViewModel {

    /*
        * This class is used to cache the data of the tour logs of a tour.
        * This is necessary to avoid unnecessary database calls.
        * The masterData is the list of all tour logs of a tour.
     */
    public class CachedData {
        public List<TourLog> masterData;
        public ObservableList<TourLog> tourLogListItems;

        public CachedData(List<TourLog> masterData, ObservableList<TourLog> tourLogListItems) {
            this.masterData = masterData;
            this.tourLogListItems = tourLogListItems;
        }

        public List<TourLog> getMasterData() {
            return masterData;
        }

        public ObservableList<TourLog> getTourLogListItems() {
            return tourLogListItems;
        }
    }

    @Autowired
    private TourLogService tourLogService;

    private List<TourLog> masterData = new ArrayList<>();
    private final ObservableList<TourLog> tourLogListItems = FXCollections.observableArrayList();

    public ObservableList<TourLog> gettourLogListListItems() {
        return tourLogListItems;
    }

    private Map<Tour, CachedData> cache = new HashMap<>();

    public void addItem(TourLog tourLog, Tour selectedTour) {
        if(selectedTour == null) {
            masterData.add(tourLog);
            tourLogListItems.add(tourLog);
        }
        else if(cache.containsKey(selectedTour)){
            cache.get(selectedTour).getMasterData().add(tourLog);
            cache.get(selectedTour).getTourLogListItems().add(tourLog);
        }
    }

    public void clearItems(){
        masterData.clear();
        tourLogListItems.clear();
    }

    public void initList(){
        tourLogService.getList().forEach(tourLog -> {
            addItem(tourLog, null);
        });
    }

    public void showLogsOfTour(Tour tour) {
        if (cache.containsKey(tour)) {
            masterData = cache.get(tour).getMasterData();
            tourLogListItems.setAll(cache.get(tour).getTourLogListItems());
        } else {
            List<TourLog> newMasterData = tourLogService.findByTourId(tour.getId());
            ObservableList<TourLog> tourLogListItems = FXCollections.observableArrayList();
            tourLogListItems.addAll(newMasterData);
            cache.put(tour, new CachedData(newMasterData, tourLogListItems));

            // overwrite master data and list items
            masterData = newMasterData;
            this.tourLogListItems.setAll(tourLogListItems);
        }
    }

    public void filterList(String searchText){
        Task<List<TourLog>> task = new Task<>() {
            @Override
            protected List<TourLog> call() throws Exception {
                updateMessage("Loading data");
                return masterData
                        .stream()
                        .filter(value -> value.getComment().toLowerCase().contains(searchText.toLowerCase())
                                || value.getId().toString().contains(searchText)
                                || value.getDifficulty().difficulty.contains(searchText)
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


}
