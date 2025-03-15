package org.fizz_buzz.mapper;

import org.fizz_buzz.dto.LocationOpenWeatherDTO;
import org.fizz_buzz.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    Location locationDTOtoLocation(LocationOpenWeatherDTO locationOpenWeatherDTO);
    LocationOpenWeatherDTO locationToLocationDTO(Location location);
}
