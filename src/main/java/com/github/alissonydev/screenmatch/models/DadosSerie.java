package com.github.alissonydev.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosSerie(
        @JsonAlias("title") String titulo ,
        @JsonAlias("totalSeason") Integer totalTemperadas ,
        @JsonAlias("imdbRating") Integer avaliacao
) {
}
