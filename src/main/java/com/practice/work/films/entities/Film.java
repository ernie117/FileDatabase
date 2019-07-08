package com.practice.work.films.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.text.WordUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

@Builder
@Data
@Document(collection = "films")
public class Film {

    public static final Comparator<Film> BY_TITLE
            = comparing(Film::getTitle);

    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    @NotBlank
    @ApiModelProperty(
            name = "title",
            example = "string",
            position = 1
    )
    private String title;

    @NotBlank
    @ApiModelProperty(
            name = "genre",
            example = "string",
            position = 2
    )
    private String genre;

    @NotBlank
    @ApiModelProperty(
            name = "director",
            example = "string",
            position = 3
    )
    private String director;

    @NotBlank
    @ApiModelProperty(
            name = "cinematographer",
            example = "string",
            position = 4
    )
    private String cinematographer;

    @NotBlank
    @ApiModelProperty(
            name = "writer",
            example = "string",
            position = 5
    )
    private String writer;

    @NotBlank
    @ApiModelProperty(
            name = "composer",
            example = "string",
            position = 6
    )
    private String composer;

    @NotBlank
    @Pattern(regexp = "\\d{4}",
             message = "Must match \\d{4}")
    @ApiModelProperty(
            name = "yearReleased",
            example = "1999",
            position = 7)
    private String yearReleased;

    @NotEmpty
    @ApiModelProperty(
            name = "actors",
            example = "['string', 'string', ...]",
            position = 8)
    @Size(min = 1, max = 10)
    public List<String> actors;

    @Override
    public String toString() {
        return String.format(
                "Film[id=%s, title=%s, genre=%s, director=%s, cinematographer=%s, writer=%s, compose=%s, yearReleased=%s, actors=%s]",
                id, title, genre, cinematographer, writer, composer, director, yearReleased, String.join(", ", actors)
        );
    }
}
