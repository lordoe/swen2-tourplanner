package at.fhtw.swen2.tutorial.service.impl;

import at.fhtw.swen2.tutorial.service.ImportExportService;
import at.fhtw.swen2.tutorial.service.TourLogService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.service.utils.TourData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
  * Implements the import and export of tours.
  * saves as json file
 */
@Service
public class ImportExportServiceImpl implements ImportExportService {
    @Autowired
    private TourService tourService;
    @Autowired
    private TourLogService tourLogService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void exportTourData(Tour tour, String path) {
        List<TourLog> tourLogs = tourLogService.findByTourId(tour.getId());
        TourData tourData = new TourData(tour, tourLogs);
        try {
            objectMapper.writeValue(new java.io.File(path), tourData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TourData importTourData(String absolutePath) {
        try {
            Path path = Paths.get(absolutePath);
            if (Files.exists(path)) {
                String json = Files.readString(path);
                TourData tourData = objectMapper.readValue(json, TourData.class);
                Tour tour = tourData.getTour();
                tour.setId(null);
                Tour saved = tourService.addNew(tour);
                tourData.setTour(saved);
                for (TourLog tourLog: tourData.getTourLogs()) {
                    tourLog.setId(null); // make sure we create a new tourLog (not update existing)
                    tourLog.setTourId(saved.getId());
                    tourLog.setId(tourLogService.addNew(tourLog).getId());
                }
                return tourData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}