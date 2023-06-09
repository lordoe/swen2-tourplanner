package at.fhtw.swen2.tutorial.service;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.utils.TourData;

public interface ImportExportService {
    void exportTourData(Tour tour, String path);
    TourData importTourData(String path);
}
