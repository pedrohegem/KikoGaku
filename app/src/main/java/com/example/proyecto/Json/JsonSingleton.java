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

    public static JsonSingleton getInstance() {
        if (instance == null) {
            instance = new JsonSingleton();
        }
        return instance;
    }

    public TreeMap<String, Municipio> getMunicipioMap() {
        return municipioMap;
    }

    public void setMunicipioMap(TreeMap<String, Municipio> municipioMap) {
        this.municipioMap = municipioMap;
    }

    public TreeMap<String, Montana> getMontanaMap() {
        return montanaMap;
    }

    public void setMontanaMap(TreeMap<String, Montana> montanaMap) {
        this.montanaMap = montanaMap;
    }
}
