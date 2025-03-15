package org.fizz_buzz.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class WeatherDTODeserializer extends StdDeserializer<WeatherOpenWeatherDTO> {
    public WeatherDTODeserializer(Class<?> vc) {
        super(vc);
    }

    public WeatherDTODeserializer() {
        super(WeatherOpenWeatherDTO.class);
    }

    @Override
    public WeatherOpenWeatherDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        var locationName = jsonNode.get("name").textValue();
        var longitude = jsonNode.get("coord").get("lon").doubleValue();
        var latitude = jsonNode.get("coord").get("lat").doubleValue();
        var country = jsonNode.get("sys").get("country").textValue();
        var windSpeed = jsonNode.get("wind").get("speed").doubleValue();
        var humidity = jsonNode.get("main").get("humidity").doubleValue();
        var temperature = jsonNode.get("main").get("temp").doubleValue();
        var icon = jsonNode.get("weather").get(0).get("icon").textValue();

        return new WeatherOpenWeatherDTO(locationName, longitude, latitude, country, windSpeed, temperature, humidity, icon);
    }
}
