package com.practice.work.films.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "films")
public class Film {

    @Id
    public String id;

    public String title;
    public String genre;
    public String director;
    public int yearReleased;
    public List<String> actors;

    @Override
    public String toString() {
        return String.format(
                "Film[id=%s, title=%s, genre=%s, director=%s, yearReleased=%d, actors=%s]",
                id, title, genre, director, yearReleased, String.join(", ", actors)
        );
    }
}
