package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.persistence.utils.Difficulty;
import at.fhtw.swen2.tutorial.presentation.utils.InvalidParamException;
import at.fhtw.swen2.tutorial.service.TourLogService;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import javafx.beans.property.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class AddTourLogWindowViewModel {

    @Autowired
    private TourLogService tourLogService;

    @Autowired
    private TourLogListViewModel tourLogListViewModel;

    @Autowired
    private TourListViewModel tourListViewModel;

    private final SimpleStringProperty comment = new SimpleStringProperty("");
    private final ObjectProperty<Integer> hour = new SimpleObjectProperty<>();
    private final ObjectProperty<Integer> minute = new SimpleObjectProperty<>();
    private final ObjectProperty<Difficulty> difficulty = new SimpleObjectProperty<>();
    private final ObjectProperty<Number> rating = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private final ObjectProperty<Long> tourId = new SimpleObjectProperty<>();


    public void init() {
        if(tourLogListViewModel.getSelected() == null) {
            initAdd();
        } else {
            initEdit();
        }
    }

    public void initAdd() {
        comment.setValue("");
        date.setValue(LocalDate.now());
        tourId.setValue(tourListViewModel.getSelected().getId());
    }

    public void initEdit() {
        TourLog selectedTourLog = tourLogListViewModel.getSelected();
        comment.setValue(selectedTourLog.getComment() != null ? selectedTourLog.getComment() : "");
        hour.setValue(selectedTourLog.getTimeInMinutes() !=  null ? selectedTourLog.getTimeInMinutes() / 60 : 0);
        minute.setValue(selectedTourLog.getTimeInMinutes() !=  null ? selectedTourLog.getTimeInMinutes() % 60 : 0);
        difficulty.setValue(selectedTourLog.getDifficulty() != null ? selectedTourLog.getDifficulty() : Difficulty.EASY);
        rating.setValue(selectedTourLog.getRating() != null ? selectedTourLog.getRating() : 0);
        date.setValue(selectedTourLog.getDateTime() != null ? selectedTourLog.getDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : LocalDate.now());
        tourId.setValue(selectedTourLog.getTourId());
    }

    public void addTourLog() throws InvalidParamException {
        if (tourLogListViewModel.getSelected() == null) {
            addNewTourLog();
        } else {
            updateTourLog();
        }
    }

    public void addNewTourLog() throws InvalidParamException {
        if(comment.getValue().isEmpty() || hour.getValue() == null
                || minute.getValue() == null || difficulty.getValue() == null
                || rating.getValue() == null || date.getValue() == null){
            throw new InvalidParamException("Not all fields set");
        }
        Tour selectedTour = tourListViewModel.getSelected();
        if(selectedTour == null) {
            throw new InvalidParamException("No tour selected");
        }

        TourLog tourLog = TourLog.builder()
                .comment(comment.getValue())
                .dateTime(Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .difficulty(difficulty.getValue())
                .timeInMinutes((hour.getValue() * 60) + minute.getValue())
                .rating((int) rating.getValue().doubleValue())
                .TourId(selectedTour.getId())
                .build();

        TourLog saved = tourLogService.addNew(tourLog);
        tourLogListViewModel.addItem(saved);
    }

    void updateTourLog() {
        TourLog selectedTourLog = tourLogListViewModel.getSelected();
        selectedTourLog.setComment(comment.getValue());
        selectedTourLog.setDateTime(Date.from(date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        selectedTourLog.setDifficulty(difficulty.getValue());
        selectedTourLog.setTimeInMinutes((hour.getValue() * 60) + minute.getValue());
        selectedTourLog.setRating((int) rating.getValue().doubleValue());

        tourLogService.update(selectedTourLog);
        tourLogListViewModel.filterList("");
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }
    public ObjectProperty<Integer> hourProperty() {
        return hour;
    }
    public ObjectProperty<Integer> minuteProperty() {
        return minute;
    }
    public ObjectProperty<Difficulty> difficultyProperty() {
        return difficulty;
    }
    public Property<Number> ratingProperty() {
        return rating;
    }
    public Property<LocalDate> dateProperty() {
        return date;
    }

    public String tourIDProperty() {
        return tourId.getValue().toString();
    }
}
