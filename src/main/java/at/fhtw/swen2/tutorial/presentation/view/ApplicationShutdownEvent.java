package at.fhtw.swen2.tutorial.presentation.view;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.context.ApplicationEvent;

public class ApplicationShutdownEvent extends ApplicationEvent {
    Map<Future<Boolean>, String> pending = new HashMap<>();

    public ApplicationShutdownEvent(Object source) {
        super(source);
    }

    void cancelShutdown(String message, Future<Boolean> future) {
        this.pending.put(future, message);
    }
}
