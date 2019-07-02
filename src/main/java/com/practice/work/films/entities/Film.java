package com.practice.work.films.entities;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.util.List;

@Document(collection = "films")
public class Film {

    @Id
    @ApiModelProperty(hidden = true)
    public String id;

    @NotBlank
    @ApiModelProperty(
            name = "title",
            example = "string",
            position = 1
    )
    public String title;

    @NotBlank
    @ApiModelProperty(
            name = "genre",
            example = "string",
            position = 2
    )
    public String genre;

    @NotBlank
    @ApiModelProperty(
            name = "director",
            example = "string",
            position = 3
    )
    public String director;

    @NotBlank
    @ApiModelProperty(
            name = "cinematographer",
            example = "string",
            position = 4
    )
    public String cinematographer;

    @NotBlank
    @ApiModelProperty(
            name = "writer",
            example = "string",
            position = 5
    )
    public String writer;

    @NotBlank
    @ApiModelProperty(
            name = "composer",
            example = "string",
            position = 5
    )
    public String composer;

    @NotNull
    @ApiModelProperty(
            name = "yearReleased",
            example = "string",
            position = 6)
    @Min(1888) @Max(9999)
    public int yearReleased;

    @ApiModelProperty(
            name = "actors",
            example = "['string', 'string', ...]",
            position = 7)
    @Size(min = 1, max = 10)
    public List<String> actors;

    @Override
    public String toString() {
        return String.format(
                "Film[id=%s, title=%s, genre=%s, director=%s, cinematographer=%s, writer=%s, compose=%s, yearReleased=%d, actors=%s]",
                id, title, genre, cinematographer, writer, composer, director, yearReleased, String.join(", ", actors)
        );
    }
}
