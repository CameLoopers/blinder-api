package com.blinder.api.characteristics.controller;

import com.blinder.api.Movie.dto.MovieRequestDto;
import com.blinder.api.Movie.mapper.MovieMapper;
import com.blinder.api.MovieCategory.dto.MovieCategoryRequestDto;
import com.blinder.api.MovieCategory.mapper.MovieCategoryMapper;
import com.blinder.api.Music.dto.MusicRequestDto;
import com.blinder.api.Music.mapper.MusicMapper;
import com.blinder.api.MusicCategory.dto.MusicCategoryRequestDto;
import com.blinder.api.MusicCategory.mapper.MusicCategoryMapper;
import com.blinder.api.TVSeries.dto.TVSeriesRequestDto;
import com.blinder.api.TVSeries.mapper.TVSeriesMapper;
import com.blinder.api.TVSeriesCategories.dto.TVSeriesCategoryRequestDto;
import com.blinder.api.TVSeriesCategories.mapper.TVSeriesCategoryMapper;
import com.blinder.api.characteristics.dto.CharacteristicsResponseDto;
import com.blinder.api.characteristics.mapper.CharacteristicsMapper;
import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.characteristics.service.CharacteristicsService;
import com.blinder.api.hobby.model.Hobby;
import com.blinder.api.hobby.service.HobbyService;
import com.blinder.api.user.model.User;
import com.blinder.api.user.security.auth.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/characteristics")
@RestController
@RequiredArgsConstructor
public class CharacteristicsController {
    private final CharacteristicsService characteristicsService;
    private final UserAuthService userAuthService;
    private final HobbyService hobbyService;

    @GetMapping
    @Operation(summary = "Get all characteristics")
    public ResponseEntity<Page<CharacteristicsResponseDto>> getAllCharacteristics(@RequestParam(name = "page", required = false) Integer page,
                                                                                  @RequestParam(name = "size", required = false) Integer size) {
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristicsService.getCharacteristics(page, size)), HttpStatus.OK);
    }

    @GetMapping("/byUser")
    @Operation(summary = "Get characteristics by userId")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> getCharacteristicsByUserId() {
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristicsService.getCharacteristicsByUserId(currentUser.getId())), HttpStatus.OK);
    }

    //Music
    @PatchMapping("/musics")
    @Operation(summary = "Add music to user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> addToMusicList(@RequestBody MusicRequestDto musicRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToMusicList(currentUser.getId(), MusicMapper.INSTANCE.musicRequestDtoToMusic(musicRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @DeleteMapping("/musics/{spotifyId}")
    @Operation(summary = "Remove music from user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMusicList(@PathVariable String spotifyId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromMusicList(currentUser.getId(), spotifyId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Music category
    @PatchMapping("/musics/categories")
    @Operation(summary = "Add music category to user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> addToMusicCategoryList(@RequestBody MusicCategoryRequestDto musicCategoryRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToMusicCategoryList(currentUser.getId(), MusicCategoryMapper.INSTANCE.musicCategoryRequestDtoToMusicCategory(musicCategoryRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @DeleteMapping("/musics/categories/{musicCategoryId}")
    @Operation(summary = "Remove music category from user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMusicCategoryList(@PathVariable String musicCategoryId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromMusicCategoryList(currentUser.getId(), musicCategoryId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Movie
    @PatchMapping("/movies")
    @Operation(summary = "Add movie to user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> addToMovieList(@RequestBody MovieRequestDto movieRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToMovieList(currentUser.getId(), MovieMapper.INSTANCE.movieRequestDtoToMovie(movieRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @DeleteMapping("/movies/{movieId}")
    @Operation(summary = "Remove movie from user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMovieList(@PathVariable String movieId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromMovieList(currentUser.getId(), movieId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Movie category
    @PatchMapping("/movies/categories")
    @Operation(summary = "Add movie category to user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> addToMovieCategoryList(@RequestBody MovieCategoryRequestDto movieCategoryRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToMovieCategoryList(currentUser.getId(), MovieCategoryMapper.INSTANCE.movieCategoryRequestDtoToMovieCategory(movieCategoryRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @DeleteMapping("/movies/categories/{movieCategoryId}")
    @Operation(summary = "Remove movie category from user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMovieCategoryList(@PathVariable String movieCategoryId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromMovieCategoryList(currentUser.getId(), movieCategoryId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Tv series
    @PatchMapping("/tvSeries")
    @Operation(summary = "Add tv series to user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> addToTvSeriesList(@RequestBody TVSeriesRequestDto tvSeriesRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToTvSeriesList(currentUser.getId(), TVSeriesMapper.INSTANCE.tvSeriesRequestDtoToTVSeries(tvSeriesRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @DeleteMapping("/tvSeries/{tvSeriesId}")
    @Operation(summary = "Remove tv series from user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> removeFromTvSeriesList(@PathVariable String tvSeriesId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromTvSeriesList(currentUser.getId(), tvSeriesId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Tv series category
    @PatchMapping("/tvSeries/categories")
    @Operation(summary = "Add tv series category to user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> addToTvSeriesCategoryList(@RequestBody TVSeriesCategoryRequestDto tvSeriesCategoryRequestDto){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.addToTvSeriesCategoryList(currentUser.getId(), TVSeriesCategoryMapper.INSTANCE.tvSeriesCategoryRequestDtoToTvSeriesCategory(tvSeriesCategoryRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @DeleteMapping("/tvSeries/categories/{tvSeriesCategoryId}")
    @Operation(summary = "Remove tv series category from user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> removeFromTvSeriesCategoryList(@PathVariable String tvSeriesCategoryId){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromTvSeriesCategoryList(currentUser.getId(), tvSeriesCategoryId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    //Hobby
    @PatchMapping("/hobby/{name}")
    @Operation(summary = "Add hobby to user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> addToHobbyList(@PathVariable String name){
        User currentUser = userAuthService.getActiveUser().getUser();
        Hobby hobby = hobbyService.getHobbyByName(name);
        Characteristics characteristics = characteristicsService.addToHobbyList(currentUser.getId(), hobby);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @DeleteMapping("/hobby/{name}")
    @Operation(summary = "Remove hobby from user's characteristics")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<CharacteristicsResponseDto> removeFromHobbyList(@PathVariable String name){
        User currentUser = userAuthService.getActiveUser().getUser();
        Characteristics characteristics = characteristicsService.removeFromHobbyList(currentUser.getId(), name);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }
}
