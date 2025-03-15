package org.fizz_buzz.dto;

public record WeatherViewDTO(Long locationId,
                             String locationName,
                             WeatherOpenWeatherDTO weatherOpenWeatherDTO) {
}
