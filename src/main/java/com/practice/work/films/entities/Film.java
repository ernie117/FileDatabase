package com.practice.work.films.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "films")
public class Film {

    @Id
    private String id;

    private String title;
    private String genre;
    private String director;
    private int yearReleased;
    private List<String> actors;

    @Override
    public String toString() {
        return String.format(
                "Film[id=%s, title=%s, genre=%s, director=%s, yearReleased=%d, actors=%s]",
                id, title, genre, director, yearReleased, String.join(", ", actors)
        );
    }
}
