package com.example.proyecto.repository.networking;

import com.example.proyecto.models.Weather;

// Interfaz encargada de delegar las llamadas a la API de OpenWeather
public interface APIManagerDelegate {

    // Método que se ejecuta tras una llamada con EXITO a la API
    public void onGetWeatherSuccess(Weather weather);

    // Método que se ejecuta tras una llamada ERRONEA a la API
    public void onGetWeatherFailure();
}
