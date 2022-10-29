package com.example.proyecto.Json;

import java.util.List;
import java.util.TreeMap;

public class JsonSingleton {
    private static JsonSingleton instance;

    public TreeMap<String, Municipio> municipioMap;
    public TreeMap<String, Montana> montanaMap;

    private JsonSingleton() {
        municipioMap = new TreeMap<String, Municipio>();
        montanaMap = new TreeMap<String, Montana>();
    }

    public static JsonSingleton getInstance() {
        if (instance == null) {
            instance = new JsonSingleton();
        }
        return instance;
    }
}
