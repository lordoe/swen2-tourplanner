package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.presentation.utils.InvalidParamException;
import at.fhtw.swen2.tutorial.presentation.utils.TransportType;
import at.fhtw.swen2.tutorial.service.MapService;
import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.utils.MapData;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.image.Image;

import java.io.File;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;

@Component
public class TourDetailsViewModel {

    @Autowired
    private TourListViewModel tourListViewModel;

    @Autowired
    private TourService tourService;

    @Autowired
    private MapService mapService;

    private final SimpleStringProperty nameString = new SimpleStringProperty("");
    private final SimpleStringProperty fromString = new SimpleStringProperty("");
    private final SimpleStringProperty toString = new SimpleStringProperty("");
    private final SimpleStringProperty descriptionString = new SimpleStringProperty("");
    private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<TransportType> transportType = new SimpleObjectProperty<>();
    private final SimpleStringProperty distanceString = new SimpleStringProperty("");
    private final SimpleStringProperty estimatedTimeString = new SimpleStringProperty("");

    public void init(){
        Tour selectedTour;
        if((selectedTour = tourListViewModel.getSelected()) == null){
            return;
        }
        nameString.setValue(selectedTour.getName());
        fromString.setValue(selectedTour.getFrom());
        toString.setValue(selectedTour.getTo());
        descriptionString.setValue(selectedTour.getDescription());
        transportType.setValue(TransportType.valueOf(selectedTour.getTransportType()));

        String imagePath = selectedTour.getImagePath();
        setMapImage(imagePath);
        setDistanceAndEstimatedTime(selectedTour);
    }

    private void setDistanceAndEstimatedTime(Tour selectedTour) {
        // for the distance and estimated time we want to have only 2 decimal places
        DecimalFormat df = new DecimalFormat("#,####.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        distanceString.setValue(df.format(selectedTour.getDistance()) + " km");
        estimatedTimeString.setValue(df.format(selectedTour.getEstimatedTime()) + " h");
    }

    private void setMapImage(String imagePath){
        if(imagePath == null || !Files.exists(Path.of(imagePath))){
            File file = new File("src/main/resources/maps/default.jpg");
            Image image = new Image(file.toURI().toString());
            imageProperty.set(image);
            return;
        }
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        imageProperty.set(image);
    }

    public SimpleStringProperty nameStringProperty() {
        return nameString;
    }

    public SimpleStringProperty fromStringProperty() { return fromString; }

    public SimpleStringProperty toStringProperty() { return toString; }

    public SimpleStringProperty descriptionStringProperty() { return descriptionString; }
    public ObjectProperty<Image> imageProperty() {return imageProperty;}
    public ObjectProperty<TransportType> transportTypeProperty() {return transportType;}
    public SimpleStringProperty distanceProperty() {return distanceString;}
    public SimpleStringProperty estimatedTimeProperty() {return estimatedTimeString;}

    public void updateTour() throws InvalidParamException {
        if(nameString.getValue() == null || nameString.getValue().isEmpty()
                || fromString.getValue() == null || fromString.getValue().isEmpty()
                || toString.getValue() == null || toString.getValue().isEmpty()
                || descriptionString.getValue() == null || descriptionString.getValue().isEmpty()
                || transportType.getValue() == null){
            throw new InvalidParamException("not all parameters are set");
        }
        Tour selectedTour;
        if((selectedTour = tourListViewModel.getSelected()) == null){
            throw new InvalidParamException("no tour selected");
        }
        MapData mapData = mapService.getMap(fromString.getValue(), toString.getValue(), transportType.getValue().toString());
        if(mapData == null){
            throw new InvalidParamException("No valid destinations are set");
        }

        selectedTour.setName(nameString.getValue());
        selectedTour.setFrom(fromString.getValue());
        selectedTour.setTo(toString.getValue());
        selectedTour.setDescription(descriptionString.getValue());
        selectedTour.setTransportType(transportType.getValue().toString());
        selectedTour.setDistance(mapData.getDistance());
        selectedTour.setEstimatedTime(mapData.getDuration());
        selectedTour.setImagePath(mapData.getImagePath());

        Tour updated = tourService.update(selectedTour);

        // update values in the view
        setMapImage(mapData.getImagePath());
        setDistanceAndEstimatedTime(updated);

        // update in List
        tourListViewModel.filterList("");
    }

}
