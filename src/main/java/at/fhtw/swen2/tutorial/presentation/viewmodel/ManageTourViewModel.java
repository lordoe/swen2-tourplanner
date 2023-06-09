package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.ImportExportService;
import at.fhtw.swen2.tutorial.service.PersonService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Person;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.utils.MapData;
import at.fhtw.swen2.tutorial.service.utils.TourData;
import javafx.beans.property.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class ManageTourViewModel {

    @Autowired
    private ImportExportService importExportService;
    @Autowired
    private TourListViewModel tourListViewModel;
    @Autowired
    private TourLogListViewModel tourLogListViewModel;

    public void exportTour(String absolutePath) {
        Tour selectedTour;
        if((selectedTour = tourListViewModel.getSelected()) == null){
            return;
        }
        importExportService.exportTourData(selectedTour, absolutePath + "/" + selectedTour.getId().toString() + "_" + selectedTour.getName() + ".json");
    }

    public void importTour(String absolutePath) throws IOException {
        TourData tourData = importExportService.importTourData(absolutePath);
        if(tourData != null){
            tourListViewModel.addItem(tourData.getTour());
            for (TourLog tourlog: tourData.getTourLogs()) {
                tourLogListViewModel.addItem(tourlog);
            }
        }
    }
}
