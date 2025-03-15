package org.fizz_buzz.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationOpenWeatherDTO(String name,
                                     Double latitude,
                                     Double longitude,
                                     String country,
                                     String state) {

    @JsonCreator
    public LocationOpenWeatherDTO(
            @JsonProperty("name") String name,
            @JsonProperty("lat") Double latitude,
            @JsonProperty("lon") Double longitude,
            @JsonProperty("country") String country,
            @JsonProperty("state") String state) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.state = state;
    }
}
