package org.fizz_buzz.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fizz_buzz.dto.LocationOpenWeatherDTO;
import org.fizz_buzz.dto.WeatherOpenWeatherDTO;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class WeatherService {

    private static final String HTTPS = "https://";
    private static final String OPEN_WEATHER_URL = "api.openweathermap.org/";
    private static final String GET_DATA = "data/2.5/weather";
    private static final String GET_CITIES = "geo/1.0/direct";

    private static final String API_KEY = "5d984879f28d14c79958006dc1934093";
    private static final String LAT = "&lat=%s";
    private static final String LON = "&lon=%s";
    private static final String CITY_NAME = "&q=%s";
    private static final String LANG = "&lang=%s";
    private static final String UNITS = "&units=%s";
    private static final String LIMIT = "&limit=%d";
    private static final String API_KEY_PARAM = "&appid=%s";

    private static final String DEFAULT_LOCALE = "en";
    private static final String DEFAULT_UNITS = "metric";


    private final HttpClient httpClient;

    public WeatherService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        var weatherService = new WeatherService(HttpClient.newHttpClient());
        var weatherLocationNameDTO = weatherService.getWeather("Ульяновск");
        System.out.println(weatherLocationNameDTO);

        var weatherCoordinatesDTO = weatherService.getWeather(35.464, 57.1275);
        System.out.println(weatherCoordinatesDTO);

        var locationDTOs = weatherService.getLocations("Москва");
        System.out.println(locationDTOs);
    }

    public WeatherOpenWeatherDTO getWeather(String locationName) throws IOException, InterruptedException {

        var uriString = new StringBuilder().append(HTTPS)
                .append(OPEN_WEATHER_URL)
                .append(GET_DATA)
                .append("?")
                .append(CITY_NAME.formatted(locationName))
                .append(LANG.formatted(DEFAULT_LOCALE))
                .append(UNITS.formatted(DEFAULT_UNITS))
                .append(API_KEY_PARAM.formatted(API_KEY))
                .toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriString))
                .GET()
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (HttpStatus.OK.value() == response.statusCode()) {
            return new ObjectMapper().readValue(response.body(), WeatherOpenWeatherDTO.class);
        } else {
            throw new RuntimeException();
        }
    }

    public WeatherOpenWeatherDTO getWeather(Double longitude, Double latitude) throws IOException, InterruptedException {

        var uriString = new StringBuilder().append(HTTPS)
                .append(OPEN_WEATHER_URL)
                .append(GET_DATA)
                .append("?")
                .append(LON.formatted(longitude))
                .append(LAT.formatted(latitude))
                .append(LANG.formatted(DEFAULT_LOCALE))
                .append(UNITS.formatted(DEFAULT_UNITS))
                .append(API_KEY_PARAM.formatted(API_KEY))
                .toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriString))
                .GET()
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (HttpStatus.OK.value() == response.statusCode()) {
            return new ObjectMapper().readValue(response.body(), WeatherOpenWeatherDTO.class);
        } else {
            throw new RuntimeException();
        }
    }

    public List<LocationOpenWeatherDTO> getLocations(String locationName) throws IOException, InterruptedException {

        var uriString = new StringBuilder().append(HTTPS)
                .append(OPEN_WEATHER_URL)
                .append(GET_CITIES)
                .append("?")
                .append(CITY_NAME.formatted(locationName))
                .append(LIMIT.formatted(5))
                .append(API_KEY_PARAM.formatted(API_KEY))
                .toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriString))
                .GET()
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (HttpStatus.OK.value() == response.statusCode()) {
            JsonParser jsonParser = new JsonFactory().createParser(response.body());

            return new ObjectMapper().readValue(jsonParser, new TypeReference<>() {
            });
        } else {
            return Collections.emptyList();
        }
    }



}
