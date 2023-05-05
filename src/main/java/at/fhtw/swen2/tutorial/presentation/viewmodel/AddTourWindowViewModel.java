package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.persistence.repositories.TourRepository;
import at.fhtw.swen2.tutorial.service.MapService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.mapper.TourMapper;
import at.fhtw.swen2.tutorial.service.utils.MapData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddTourWindowViewModel {

    @Autowired
    private TourService tourService;
    @Autowired
    private MapService mapService;

    @Autowired
    private TourListViewModel tourListViewModel;

    private final SimpleStringProperty nameString = new SimpleStringProperty("");
    private final SimpleStringProperty fromString = new SimpleStringProperty("");
    private final SimpleStringProperty toString = new SimpleStringProperty("");
    private final SimpleStringProperty descriptionString = new SimpleStringProperty("");

    public Tour addTour() {
        if(nameString.getValue().isEmpty() || fromString.getValue().isEmpty()
                || toString.getValue().isEmpty() || descriptionString.getValue().isEmpty()){
            return null;
        }
        MapData mapData = mapService.getMap(fromString.getValue(), toString.getValue(), "fastest");

        Tour newTour = Tour.builder()
                .name(nameString.getValue())
                .from(fromString.getValue())
                .to(toString.getValue())
                .description(descriptionString.getValue())
                .transportType("bicycle")
                .distance(mapData != null ? mapData.getDistance() : 0)
                .estimatedTime(mapData != null ? mapData.getDuration() : 0)
                .imagePath(mapData != null ? mapData.getImagePath() : null)
                .build();

        Tour savedTour = tourService.addNew(newTour);
        tourListViewModel.addItem(savedTour);
        return savedTour;
    }

    public SimpleStringProperty nameStringProperty() {
        return nameString;
    }

    public SimpleStringProperty fromStringProperty() { return fromString; }

    public SimpleStringProperty toStringProperty() { return toString; }

    public SimpleStringProperty descriptionStringProperty() { return descriptionString; }
}
