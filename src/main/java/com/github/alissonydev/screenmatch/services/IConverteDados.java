package com.github.alissonydev.screenmatch.services;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classeTipo);
}
