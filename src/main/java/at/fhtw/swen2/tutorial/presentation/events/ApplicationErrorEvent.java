package at.fhtw.swen2.tutorial.presentation.events;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

public class ApplicationErrorEvent extends ApplicationEvent {
    @Getter private final String message;
    @Getter private final Throwable throwable;

    public ApplicationErrorEvent(Object source, String message, Throwable throwable) {
        super(source);

        this.message = message;
        this.throwable = throwable;
    }
}
