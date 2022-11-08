package com.example.proyecto.utils;

import com.example.proyecto.Room.Modelo.Weather;

// Interfaz encargada de delegar las llamadas a la API de OpenWeather
public interface APIManagerDelegate {
    public void onGetWeatherSuccess(Weather weather);
}
