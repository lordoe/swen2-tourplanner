package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.viewmodel.LogEntry;
import at.fhtw.swen2.tutorial.presentation.viewmodel.LogEntry.Type;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
@Slf4j
public class LogViewController implements Initializable {

    @FXML
    TableView<LogEntry> logTable;
    @FXML
    Button clearButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        initilizeClearButton();
    }

    void initilizeClearButton() {
        clearButton.disableProperty().bind(Bindings.isEmpty(logTable.getItems()));
    }

    @SuppressWarnings("unchecked")
    void initializeTable() {
        ObservableList<LogEntry> entries = FXCollections.observableArrayList();
        logTable.setItems(entries); // TODO load real log data
        logTable.setPlaceholder(new Label(""));

        TableColumn<LogEntry, LocalDateTime> dateColumn = new TableColumn<>("Date");
        dateColumn.prefWidthProperty().bind(logTable.widthProperty().multiply(0.25));
        dateColumn.setCellValueFactory(value -> value.getValue().getDate());
        dateColumn.setCellFactory(tableColumn -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                if (empty || item == null) this.setText("");
                else this.setText(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
            }
        });

        TableColumn<LogEntry, Type> typeColumn = new TableColumn<>("Type");
        typeColumn.setSortable(false);
        typeColumn.prefWidthProperty().bind(logTable.widthProperty().multiply(0.1));
        typeColumn.setCellValueFactory(value -> value.getValue().getType()); // replaced with image

        TableColumn<LogEntry, String> systemColumn = new TableColumn<>("System");
        systemColumn.setSortable(false);
        systemColumn.prefWidthProperty().bind(logTable.widthProperty().multiply(0.15));
        systemColumn.setCellValueFactory(value -> value.getValue().getSystemName());

        TableColumn<LogEntry, String> messageColumn = new TableColumn<>("Message");
        messageColumn.setSortable(false);
        messageColumn.prefWidthProperty().bind(logTable.widthProperty().multiply(0.5));
        messageColumn.setCellValueFactory(value -> value.getValue().getMessage());
        messageColumn.setMaxWidth(Double.MAX_VALUE);

        logTable.getColumns().addAll(dateColumn, typeColumn, systemColumn, messageColumn);
    }

    @FXML
    public void clearLogs(Event event) {
        log.debug("Clearing debug log data");
        logTable.getItems().clear();
    }
}
