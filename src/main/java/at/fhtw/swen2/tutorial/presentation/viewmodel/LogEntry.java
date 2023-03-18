package at.fhtw.swen2.tutorial.presentation.viewmodel;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LogEntry {
    public static enum Type {
        ERROR,
        WARNING,
        INFO
    }

    Property<Type> type = new SimpleObjectProperty<>(this, "type");
    Property<LocalDateTime> date = new SimpleObjectProperty<>(this, "date");
    Property<String> systemName = new SimpleStringProperty(this, "systemName");
    Property<String> message = new SimpleStringProperty(this, "message");

    public LogEntry(Type type, String systemName, String message) {
        this.type.setValue(type);
        this.date.setValue(LocalDateTime.now());
        this.systemName.setValue(systemName);
        this.message.setValue(message);
    }
}
