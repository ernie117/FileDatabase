package com.practice.work.films.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO {

    /**
     * Auto-generated MongoDB ID, unique to a document
     */
    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    /**
     * Title of the film
     */
    @NotBlank
    @ApiModelProperty(
            name = "title",
            example = "string",
            position = 1
    )
    private String title;

    /**
     * Genre of the film
     */
    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\s]+")
    @ApiModelProperty(
            name = "genre",
            example = "string",
            position = 2
    )
    private String genre;

    /**
     * Director of the film
     */
    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\s]+")
    @ApiModelProperty(
            name = "director",
            example = "string",
            position = 3
    )
    private String director;

    /**
     * Cinematographer of the film
     */
    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\s]+")
    @ApiModelProperty(
            name = "cinematographer",
            example = "string",
            position = 4
    )
    private String cinematographer;

    /**
     * Writer of the film
     */
    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\s]+")
    @ApiModelProperty(
            name = "writer",
            example = "string",
            position = 5
    )
    private String writer;

    /**
     * Composer of the film
     */
    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\s]+")
    @ApiModelProperty(
            name = "composer",
            example = "string",
            position = 6
    )
    private String composer;

    /**
     * Year/month/day the film was released
     */
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past
    @ApiModelProperty(
            name = "releaseDate",
            example = "1999-01-31",
            position = 7)
    private LocalDate releaseDate;

    /**
     * List of the main actors
     */
    @NotEmpty
    @ApiModelProperty(
            name = "actors",
            example = "['string', 'string', ...]",
            position = 8)
    @Size(min = 1, max = 10)
    private List<String> actors;

    @ApiModelProperty(hidden = true)
    @CreatedDate
    private LocalDateTime dateAdded;

}

