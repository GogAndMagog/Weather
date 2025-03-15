package org.fizz_buzz.mapper;

import org.fizz_buzz.dto.LocationOpenWeatherDTO;
import org.fizz_buzz.model.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationMapperTest {

    private static final String LOCATION_NAME = "London";
    private static final Double LOCATION_LATITUDE = 12.0;
    private static final Double LOCATION_LONGITUDE = 33.0;

    @Test
    void locationDTOtoLocation() {

        LocationMapper locationMapper = LocationMapper.INSTANCE;

        var locationDTO = createLocationDTO();

        var location = locationMapper.locationDTOtoLocation(locationDTO);

        assertEquals(LOCATION_NAME, location.getName());
        assertEquals(LOCATION_LATITUDE, location.getLatitude());
        assertEquals(LOCATION_LONGITUDE, location.getLongitude());
    }

    @Test
    void locationToLocationDTO() {

        LocationMapper locationMapper = LocationMapper.INSTANCE;

        var location = createLocation();

        var locationDTO = locationMapper.locationToLocationDTO(location);

        assertEquals(LOCATION_NAME, locationDTO.name());
        assertEquals(LOCATION_LATITUDE, locationDTO.latitude());
        assertEquals(LOCATION_LONGITUDE, locationDTO.longitude());
    }

    private Location createLocation(){
        return new Location(LOCATION_NAME, LOCATION_LATITUDE, LOCATION_LONGITUDE);
    }

    private LocationOpenWeatherDTO createLocationDTO(){
        return new LocationOpenWeatherDTO(LOCATION_NAME, LOCATION_LATITUDE, LOCATION_LONGITUDE, "", "");
    }
}