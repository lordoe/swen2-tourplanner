package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.viewmodel.NewPersonViewModel;
import at.fhtw.swen2.tutorial.service.PersonService;
import at.fhtw.swen2.tutorial.service.model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
//@Scope("prototype")
@Slf4j
public class NewPersonController implements Initializable {

    @Autowired
    private PersonService personService;
    @Autowired
    private SearchController searchController;
    @Autowired
    private NewPersonViewModel newPersonViewModel;

    @FXML
    private Text feedbackText;
    @FXML
    private TextField nameTextField;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        nameTextField.textProperty().bindBidirectional(newPersonViewModel.nameProperty());
    }

    public void submitButtonAction(ActionEvent event) {
        if (nameTextField.getText().isEmpty()) {
            feedbackText.setText("nothing entered!");
            return;
        }

        newPersonViewModel.addNewPerson();
    }
}
