package com.practice.work.films.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "films")
@EnableConfigurationProperties
@Getter
@Setter
public class ConfigProperties {

    private String addMultipleFilmsURI;
    private String addFilmURI;
    private String addFilmHttpURI;
    private String findFilmsByActorURI;
    private String findAllFilmsURI;
    private String findFilmsByReleaseDateURI;
    private String findFilmsByReleaseYearURI;
    private String findFilmsByGenreURI;
    private String findFilmsByDirectorURI;
    private String findFilmsByComposerURI;
    private String findFilmsByCinematographerURI;
    private String findFilmsByWriterURI;
    private String findFilmByTitleURI;
    private String getFilmIdURI;

}
