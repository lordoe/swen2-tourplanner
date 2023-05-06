package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.presentation.utils.InvalidParamException;
import at.fhtw.swen2.tutorial.presentation.utils.TransportType;
import at.fhtw.swen2.tutorial.service.MapService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.utils.MapData;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    private final ObjectProperty<TransportType> transportType = new SimpleObjectProperty<>();

    public void init(){
        nameString.setValue("");
        fromString.setValue("");
        toString.setValue("");
        descriptionString.setValue("");
    }

    public void addTour() throws InvalidParamException {
        if(nameString.getValue().isEmpty() || fromString.getValue().isEmpty()
                || toString.getValue().isEmpty() || descriptionString.getValue().isEmpty()
                || transportType.getValue() == null){
            throw new InvalidParamException("Not all fields set");
        }
        MapData mapData = mapService.getMap(fromString.getValue(), toString.getValue(), transportType.getValue().transportType);
        if(mapData == null){
            throw new InvalidParamException("Route start or destination are not valid");
        }


        Tour newTour = Tour.builder()
                .name(nameString.getValue())
                .from(fromString.getValue())
                .to(toString.getValue())
                .description(descriptionString.getValue())
                .transportType(transportType.getValue().toString())
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

    public ObjectProperty<TransportType> transportTypeProperty() { return transportType; }

}
