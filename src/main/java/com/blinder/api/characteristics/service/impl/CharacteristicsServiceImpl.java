package com.blinder.api.characteristics.service.impl;

import com.blinder.api.Movie.model.Movie;
import com.blinder.api.Movie.repository.MovieRepository;
import com.blinder.api.MovieCategory.model.MovieCategory;
import com.blinder.api.MovieCategory.repository.MovieCategoryRepository;
import com.blinder.api.Music.model.Music;
import com.blinder.api.Music.repository.MusicRepository;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.MusicCategory.repository.MusicCategoryRepository;
import com.blinder.api.TVSeries.model.TVSeries;
import com.blinder.api.TVSeries.repository.TVSeriesRepository;
import com.blinder.api.TVSeriesCategories.model.TVSeriesCategory;
import com.blinder.api.TVSeriesCategories.repository.TVSeriesCategoryRepository;
import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.characteristics.repository.CharacteristicsRepository;
import com.blinder.api.characteristics.rules.CharacteristicsBusinessRules;
import com.blinder.api.characteristics.service.CharacteristicsService;
import com.blinder.api.hobby.model.Hobby;
import com.blinder.api.hobby.repository.HobbyRepository;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.UserRepository;
import com.blinder.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class CharacteristicsServiceImpl implements CharacteristicsService {
    private final CharacteristicsRepository characteristicsRepository;
    private final MusicRepository musicRepository;
    private final MusicCategoryRepository musicCategoryRepository;
    private final MovieRepository movieRepository;
    private final MovieCategoryRepository movieCategoryRepository;
    private final TVSeriesRepository tvSeriesRepository;
    private final TVSeriesCategoryRepository tvSeriesCategoryRepository;
    private final HobbyRepository hobbyRepository;
    private final UserRepository userRepository;
    private final CharacteristicsBusinessRules characteristicsBusinessRules;

    @Override
    public Characteristics addCharacteristics(Characteristics characteristics) {
        User user = userRepository.findById(characteristics.getUser().getId()).orElseThrow();
        Characteristics newCharacteristics = characteristicsRepository.save(characteristics);

        user.setCharacteristics(newCharacteristics);
        userRepository.save(user);
        characteristicsRepository.save(newCharacteristics);

        return newCharacteristics;
    }

    @Override
    public Page<Characteristics> getCharacteristics(Integer page, Integer size) {
        boolean isPageble = Objects.nonNull(page) && Objects.nonNull(size);

        if(!isPageble){
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.characteristicsRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Characteristics getCharacteristicsByUserId(String userId) {
        return this.characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
    }

    @Override
    public Characteristics updateCharacteristics(String characteristicsId, Characteristics characteristics) {
        Characteristics characteristicsToUpdate = this.characteristicsRepository.findById(characteristicsId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(characteristics);

        BeanUtils.copyProperties(characteristics, characteristicsToUpdate, nullPropertyNames.toArray(new String[0]));

        this.characteristicsRepository.save(characteristicsToUpdate);
        return characteristicsToUpdate;
    }

    @Override
    public void deleteCharacteristics(String characteristicsId) {
        this.characteristicsRepository.deleteById(characteristicsId);
    }

    //Music
    @Override
    public Characteristics addToMusicList(String userId, Music music) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfMusicAlreadyExistsInList(characteristics, music.getSpotifyId());

        Music addedMusic = musicRepository.save(music);
        characteristics.addToMusicList(addedMusic);
        characteristicsRepository.save(characteristics);

        return characteristics;
    }

    @Transactional
    @Override
    public Characteristics removeFromMusicList(String userId, String spotifyId) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfMusicExistsInList(characteristics, spotifyId);

        Music musicToRemove = musicRepository.findBySpotifyId(spotifyId).orElseThrow();
        characteristics.removeFromMusicList(musicToRemove);
        characteristicsRepository.save(characteristics);
        musicRepository.deleteBySpotifyId(spotifyId);

        return characteristics;
    }

    //Music category
    @Override
    public Characteristics addToMusicCategoryList(String userId, MusicCategory musicCategory) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfMusicCategoryAlreadyExistsInList(characteristics, musicCategory.getName());

        MusicCategory addedMusicCategory = musicCategoryRepository.save(musicCategory);
        characteristics.addToMusicCategoryList(addedMusicCategory);
        characteristicsRepository.save(characteristics);

        return characteristics;
    }

    @Override
    public Characteristics removeFromMusicCategoryList(String userId, String musicCategoryId) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfMusicCategoryExistsInList(characteristics, musicCategoryId);

        MusicCategory musicCategoryToRemove = musicCategoryRepository.findById(musicCategoryId).orElseThrow();
        characteristics.removeFromMusicCategoryList(musicCategoryToRemove);
        characteristicsRepository.save(characteristics);
        musicCategoryRepository.deleteById(musicCategoryId);

        return characteristics;
    }

    //Movie
    @Override
    public Characteristics addToMovieList(String userId, Movie movie) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfMovieAlreadyExistsInList(characteristics, movie.getImdbId());

        Movie addedMovie = movieRepository.save(movie);
        characteristics.addToMovieList(addedMovie);
        characteristicsRepository.save(characteristics);

        return characteristics;
    }

    @Override
    public Characteristics removeFromMovieList(String userId, String movieId) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfMovieExistsInList(characteristics, movieId);

        Movie movieToRemove = movieRepository.findById(movieId).orElseThrow();
        characteristics.removeFromMovieList(movieToRemove);
        characteristicsRepository.save(characteristics);
        movieRepository.deleteById(movieId);
        return characteristics;
    }

    //Movie category
    @Override
    public Characteristics addToMovieCategoryList(String userId, MovieCategory movieCategory) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfMovieCategoryAlreadyExistsInList(characteristics, movieCategory.getName());

        MovieCategory addedMovieCategory = movieCategoryRepository.save(movieCategory);
        characteristics.addToMovieCategoryList(addedMovieCategory);
        characteristicsRepository.save(characteristics);

        return characteristics;
    }

    @Override
    public Characteristics removeFromMovieCategoryList(String userId, String movieCategoryId) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfMovieCategoryExistsInList(characteristics, movieCategoryId);

        MovieCategory movieCategoryToRemove = movieCategoryRepository.findById(movieCategoryId).orElseThrow();
        characteristics.removeFromMovieCategoryList(movieCategoryToRemove);
        characteristicsRepository.save(characteristics);
        movieRepository.deleteById(movieCategoryId);

        return characteristics;
    }

    //Tv series
    @Override
    public Characteristics addToTvSeriesList(String userId, TVSeries tvSeries) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfTvSeriesAlreadyExistsInList(characteristics, tvSeries.getImdbId());

        TVSeries addedTvSeries = tvSeriesRepository.save(tvSeries);
        characteristics.addToTvSeriesList(addedTvSeries);
        characteristicsRepository.save(characteristics);

        return characteristics;
    }

    @Override
    public Characteristics removeFromTvSeriesList(String userId, String tvSeriesId) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfTvSeriesExistsInList(characteristics, tvSeriesId);

        TVSeries tvSeriesToRemove = tvSeriesRepository.findById(tvSeriesId).orElseThrow();
        characteristics.removeFromTvSeriesList(tvSeriesToRemove);
        characteristicsRepository.save(characteristics);
        movieRepository.deleteById(tvSeriesId);

        return characteristics;
    }

    //Tv series category
    @Override
    public Characteristics addToTvSeriesCategoryList(String userId, TVSeriesCategory tvSeriesCategory) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfTvSeriesCategoryAlreadyExistsInList(characteristics, tvSeriesCategory.getName());

        TVSeriesCategory addedTvSeriesCategory = tvSeriesCategoryRepository.save(tvSeriesCategory);
        characteristics.addToTvSeriesCategoryList(addedTvSeriesCategory);
        characteristicsRepository.save(characteristics);

        return characteristics;
    }

    @Override
    public Characteristics removeFromTvSeriesCategoryList(String userId, String tvSeriesCategoryId) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfTvSeriesCategoryExistsInList(characteristics, tvSeriesCategoryId);

        TVSeriesCategory tvSeriesCategoryToRemove = tvSeriesCategoryRepository.findById(tvSeriesCategoryId).orElseThrow();
        characteristics.removeFromTvSeriesCategoryList(tvSeriesCategoryToRemove);
        characteristicsRepository.save(characteristics);
        movieRepository.deleteById(tvSeriesCategoryId);

        return characteristics;
    }

    //Hobby
    @Override
    public Characteristics addToHobbyList(String userId, Hobby hobby) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfHobbyAlreadyExistsInList(characteristics, hobby.getName());

        Hobby addedHobby = hobbyRepository.save(hobby);
        characteristics.addToHobbyList(addedHobby);
        characteristicsRepository.save(characteristics);

        return characteristics;
    }

    @Override
    public Characteristics removeFromHobbyList(String userId, String hobbyName) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        characteristicsBusinessRules.checkIfHobbyExistsInList(characteristics, hobbyName);

        Hobby hobbyToRemove = hobbyRepository.findHobbyByName(hobbyName).orElseThrow();
        characteristics.removeFromHobbyList(hobbyToRemove);
        characteristicsRepository.save(characteristics);
        hobbyRepository.deleteByName(hobbyName);

        return characteristics;
    }
}
