package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.TourService;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.image.Image;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class TourDetailsViewModel {

    @Autowired
    private TourListViewModel tourListViewModel;

    @Autowired
    private TourService tourService;

    private final SimpleStringProperty nameString = new SimpleStringProperty("");
    private final SimpleStringProperty fromString = new SimpleStringProperty("");
    private final SimpleStringProperty toString = new SimpleStringProperty("");
    private final SimpleStringProperty descriptionString = new SimpleStringProperty("");
    private final ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

    public void init(){
        Tour selectedTour;
        if((selectedTour = tourListViewModel.getSelected()) == null){
            return;
        }
        nameString.setValue(selectedTour.getName());
        fromString.setValue(selectedTour.getFrom());
        toString.setValue(selectedTour.getTo());
        descriptionString.setValue(selectedTour.getDescription());

        // for debugging print current path
        //System.err.println("Current path: " + System.getProperty("user.dir"));
        //System.out.println(Files.exists(Path.of("/home/lorenz/fh/4_sem/swen/swen2-tourplanner/src/main/resources/maps/Prag_Linz.png")));
        File file = new File(selectedTour.getImagePath());
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

    public Tour updateTour() {
        if(nameString.getValue() == null || nameString.getValue().isEmpty()
                || fromString.getValue() == null || fromString.getValue().isEmpty()
                || toString.getValue() == null || toString.getValue().isEmpty()
                || descriptionString.getValue() == null || descriptionString.getValue().isEmpty()){
            return null;
        }
        Tour selectedTour;
        if((selectedTour = tourListViewModel.getSelected()) == null){
            return null;
        }
        selectedTour.setName(nameString.getValue());
        selectedTour.setFrom(fromString.getValue());
        selectedTour.setTo(toString.getValue());
        selectedTour.setDescription(descriptionString.getValue());

        Tour updated = tourService.update(selectedTour);
        tourListViewModel.updateTour(updated);
        return updated;
    }

}
