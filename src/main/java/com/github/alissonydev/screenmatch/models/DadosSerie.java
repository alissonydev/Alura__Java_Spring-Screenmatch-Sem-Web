package com.github.alissonydev.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(
        @JsonAlias("title") String titulo ,
        @JsonAlias("totalSeason") Integer totalTemperadas ,
        @JsonAlias("imdbRating") String avaliacao
) {
}
