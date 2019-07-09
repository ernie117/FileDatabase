package com.practice.work.films.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

@Getter @Setter
@RequiredArgsConstructor
public class ConfirmedDeletionMessage {

    private final String title;
    private final String message
            = String.format("'%s': successfully deleted", getTitle());
}
