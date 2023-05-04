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

    public void addTourLog(TourLog tourLog) {
        Tour selectedTour = tourListViewModel.getSelected();
        if(selectedTour == null || tourLog == null) {
            return;
        }
        tourLog.setTourId(selectedTour.getId());
        TourLog saved = tourLogService.addNew(tourLog);
        tourLogListViewModel.addItem(saved);
        tourLogListViewModel.showLogsOfTour(selectedTour);
    }
}
