package com.blinder.api.characteristics.model;

import com.blinder.api.Movie.model.Movie;
import com.blinder.api.MovieCategory.model.MovieCategory;
import com.blinder.api.Music.model.Music;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.TVSeries.model.TVSeries;
import com.blinder.api.TVSeriesCategories.model.TVSeriesCategory;
import com.blinder.api.hobby.model.Hobby;
import com.blinder.api.model.BaseEntity;
import com.blinder.api.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
public class Characteristics extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Movie> movies = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<MovieCategory> movieCategories = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<Music> musics = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<MusicCategory> musicCategories = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<TVSeries> tvSeriesList = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<TVSeriesCategory> tvSeriesCategories = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<Hobby> hobbies = new ArrayList<>();

    /*@OneToMany
    private List<Book> books;
    @OneToMany
    private List<BookCategory> bookCategories;*/

    //Music and music category
    public void addToMusicList(Music music){ musics.add(music); }
    public void removeFromMusicList(Music music){ musics.remove(music); }
    public void addToMusicCategoryList(MusicCategory musicCategory){ musicCategories.add(musicCategory); }
    public void removeFromMusicCategoryList(MusicCategory musicCategory){ musicCategories.remove(musicCategory); }

    //Movie and movie category
    public void addToMovieList(Movie movie){ movies.add(movie); }
    public void removeFromMovieList(Movie movie){ movies.remove(movie); }
    public void addToMovieCategoryList(MovieCategory movieCategory){ movieCategories.add(movieCategory); }
    public void removeFromMovieCategoryList(MovieCategory movieCategory){ movieCategories.remove(movieCategory); }

    //Tv series and tv series category
    public void addToTvSeriesList(TVSeries tvSeries){ tvSeriesList.add(tvSeries); }
    public void removeFromTvSeriesList(TVSeries tvSeries){ tvSeriesList.remove(tvSeries); }
    public void addToTvSeriesCategoryList(TVSeriesCategory tvSeriesCategory){ tvSeriesCategories.add(tvSeriesCategory); }
    public void removeFromTvSeriesCategoryList(TVSeriesCategory tvSeriesCategory){ tvSeriesCategories.remove(tvSeriesCategory); }

    //Hobby
    public void addToHobbyList(Hobby hobby){ hobbies.add(hobby); }
    public void removeFromHobbyList(Hobby hobby){ hobbies.remove(hobby); }

}


