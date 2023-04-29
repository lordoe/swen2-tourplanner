package at.fhtw.swen2.tutorial.presentation.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchTourLogViewModel {

    @Autowired
    private TourLogListViewModel tourLogListViewModel;

    private final SimpleStringProperty searchString = new SimpleStringProperty();

    public String getSearchString() {
        return searchString.get();
    }

    public SimpleStringProperty searchStringProperty() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString.set(searchString);
    }

    public void search(){
        tourLogListViewModel.filterList(getSearchString());
    }



}
