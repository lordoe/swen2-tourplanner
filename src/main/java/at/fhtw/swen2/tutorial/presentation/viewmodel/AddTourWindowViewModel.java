package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.presentation.utils.MissingParamException;
import at.fhtw.swen2.tutorial.service.MapService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.utils.MapData;
import javafx.beans.property.SimpleStringProperty;
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

    public void addTour() throws MissingParamException {
        if(nameString.getValue().isEmpty() || fromString.getValue().isEmpty()
                || toString.getValue().isEmpty() || descriptionString.getValue().isEmpty()){
            throw new MissingParamException("Not all fields set");
        }
        MapData mapData = mapService.getMap(fromString.getValue(), toString.getValue(), "fastest");
        if(mapData == null){
            throw new MissingParamException("Route start or destination are not valid");
        }


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
    }

    public SimpleStringProperty nameStringProperty() {
        return nameString;
    }

    public SimpleStringProperty fromStringProperty() { return fromString; }

    public SimpleStringProperty toStringProperty() { return toString; }

    public SimpleStringProperty descriptionStringProperty() { return descriptionString; }
}
