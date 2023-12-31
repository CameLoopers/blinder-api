package com.blinder.api.location.mapper;
import com.blinder.api.location.dto.LocationCountryDto;
import com.blinder.api.location.dto.LocationDto;
import com.blinder.api.location.dto.LocationStateDto;
import com.blinder.api.location.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    List<LocationCountryDto> locationToLocationCountryDto(List<Location> locations);
    List<LocationStateDto> locationToLocationStateDto(List<Location> locations);


    @Mapping(source = "countryIso2", target = "iso2")
    LocationCountryDto locationToLocationCountryDto(Location location);

    @Mapping(source = "stateIso2", target = "iso2")
    LocationStateDto locationToLocationStateDto(Location location);

    Location locationDtoToLocation(LocationDto locationDto);

    LocationDto locationToLocationDto(Location location);

    Location locationCountryDtoToLocation(LocationCountryDto locationCountryDto);
    Location locationStateDtoToLocation(LocationStateDto locationStateDto);


    default Page<LocationCountryDto> locationToLocationCountryDto(Page<Location> locationPage) {
        List<LocationCountryDto> responseDtoList = locationToLocationCountryDto(locationPage.getContent());
        return new PageImpl<>(responseDtoList, locationPage.getPageable(), locationPage.getTotalElements());
    }

    default Page<LocationStateDto> locationToLocationStateDto(Page<Location> locationPage) {
        List<LocationStateDto> responseDtoList = locationToLocationStateDto(locationPage.getContent());
        return new PageImpl<>(responseDtoList, locationPage.getPageable(), locationPage.getTotalElements());
    }
}

