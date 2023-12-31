package com.blinder.api.Movie.mapper;

import com.blinder.api.Movie.dto.MovieRequestDto;
import com.blinder.api.Movie.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;



@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    Movie movieRequestDtoToMovie(MovieRequestDto movieRequestDto);
}