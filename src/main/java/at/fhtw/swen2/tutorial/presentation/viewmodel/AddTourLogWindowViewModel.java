package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.TourLogService;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddTourLogWindowViewModel {

    @Autowired
    private TourLogService tourLogService;

    @Autowired
    private TourLogListViewModel tourLogListViewModel;

    @Autowired
    private TourListViewModel tourListViewModel;

    public TourLog addTourLog(TourLog tourLog) {
        Tour selectedTour = tourListViewModel.getSelected();
        if(selectedTour == null || tourLog == null) {
            return null;
        }
        tourLog.setTour(selectedTour);
        // debug
        System.out.println("debug1: " + selectedTour.getTourLogs());
        TourLog savedTourLog = tourLogService.addNew(tourLog);
        System.out.println("debug2: " + selectedTour.getTourLogs());
        selectedTour.addTourLog(savedTourLog);
        tourLogListViewModel.addItem(savedTourLog);
        // TODO:: fix bug (has todo with tourLogService, since after addNew, the tourlogs are null
        return savedTourLog;
    }
}
