package com.practice.work.films.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.practice.work.films.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .tags(
                        new Tag("Add Film", "REST endpoint to add a new film to the mongoDB"),
                        new Tag("Add Film via HTTP call", "REST endpoint to add a new film to the mongoDB using HTTP params"),
                        new Tag("Add Multiple Films", "REST endpoint to add multiple new films to the mongoDB"),
                        new Tag("Fetch All Films", "REST endpoint to fetch a list of all films in the mongoDB"),
                        new Tag("Fetch Films by Director", "REST endpoint to fetch a list of all film by the same director from the mongoDB"),
                        new Tag("Fetch Films by Title", "REST endpoint to find a film by searching its title"),
                        new Tag("Fetch Films by Genre", "REST endpoint to fetch a list of all films by one genre from the mongoDB"),
                        new Tag("Fetch Films by Exact Date", "REST endpoint to fetch a list of all films by exact date from the mongoDB"),
                        new Tag("Fetch Films by Release Year", "REST endpoint to fetch a list of all films by their release year from the mongoDB"),
                        new Tag("Fetch Film IDs by Title", "REST endpoint to fetch a list of film IDs by title"),
                        new Tag("Delete Film by ID", "REST endpoint to fetch a list of all films by one year from the mongoDB"),
                        new Tag("Fetch Films by Actor", "REST endpoint to fetch a list of all films by actor from the mongoDB"),
                        new Tag("Fetch Films by Composer", "REST endpoint to fetch a list of all films by composer from the mongoDB"),
                        new Tag("Fetch Films by Cinematographer", "REST endpoint to fetch a list of all films by cinematographer from the mongoDB"),
                        new Tag("Fetch Films by Writer", "REST endpoint to fetch a list of all films by writer from the mongoDB")
                );
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Film MongoDB REST Service",
                "Provides endpoints to query my mongo film database",
                "0.0.1",
                "",
                new Contact("Matthew Stephens", "", ""),
                "", "", Collections.emptyList()
        );
    }
}
