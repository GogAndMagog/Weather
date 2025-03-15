package org.fizz_buzz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = WeatherDTODeserializer.class)
public record WeatherOpenWeatherDTO(String locationName,
                                    Double latitude,
                                    Double longitude,
                                    String country,
                                    Double windSpeed,
                                    Double temperature,
                                    Double humidity,
                                    String icon) {
}
