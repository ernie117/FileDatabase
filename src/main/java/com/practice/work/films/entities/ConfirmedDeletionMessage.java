package com.practice.work.films.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmedDeletionMessage {

    private final String title;
    private final String message;

    public ConfirmedDeletionMessage(String title) {
        this.title = title;
        message = String.format("'%s': successfully deleted.", title);
    }
}
