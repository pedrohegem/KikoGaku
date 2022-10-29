package com.example.proyecto.Json;

import java.util.List;
import java.util.TreeMap;

public class JsonSingleton {
    private static JsonSingleton instance;

    private TreeMap<String, Municipio> municipioMap;
    private TreeMap<String, Montana> montanaMap;

    private JsonSingleton() {
        municipioMap = new TreeMap<String, Municipio>();
        montanaMap = new TreeMap<String, Montana>();
    }

    public static JsonSingleton getInstance(String value) {
        if (instance == null) {
            instance = new JsonSingleton();
        }
        return instance;
    }
    //TODO Mejorar Listas y implemetar metodos


}
