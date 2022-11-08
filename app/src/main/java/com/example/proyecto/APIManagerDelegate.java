package com.example.proyecto;

import com.example.proyecto.Room.Modelo.Weather;

// Interfaz encargada de delegar las llamadas a la API de OpenWeather
public interface APIManagerDelegate {
    public void onGetWeatherSuccess(Weather weather);
}
