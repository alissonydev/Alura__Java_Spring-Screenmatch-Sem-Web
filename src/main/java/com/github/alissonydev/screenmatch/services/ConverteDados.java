package com.github.alissonydev.screenmatch.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alissonydev.screenmatch.models.DadosSerie;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json , Class<T> classeTipo) {
        try {
            return mapper.readValue(json, classeTipo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
