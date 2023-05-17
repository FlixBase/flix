package flixbase.flix.dto;


import java.util.List;
import java.util.stream.Collectors;

import flixbase.flix.entity.FilmRating;
import flixbase.flix.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor  
@AllArgsConstructor 
public class MovieDto {

    private Integer id;

    private String title;

    private String description;

    private String posterUrl;

    private FilmRating filmRating;

    private String year;

    private String actors;

    private String tags;

    private List<GenreDto> genres;

    private List<ViewDto> views;


    public MovieDto(Movie movie) {
        if(movie.getId() != null) {
            this.id = movie.getId();
        } 
        if(movie.getTitle() != null) {
            this.title = movie.getTitle();
        }
        if(movie.getDescription() != null) {
            this.description = movie.getDescription();
        }
        if(movie.getFilmRating() != null) {
            this.filmRating = movie.getFilmRating();
        }
        if(movie.getPosterUrl() != null) {
            this.posterUrl = movie.getPosterUrl();
        }
        if(movie.getYear() != null) {
            this.year = movie.getYear();
        }
        if(movie.getActors() != null) {
            this.actors = movie.getActors();
        }
        if(movie.getTags() != null) {
            this.tags = movie.getTags();
        }
        if(movie.getGenres() != null) {
            this.genres = movie.getGenres().stream().map(genre -> new GenreDto(genre)).collect(Collectors.toList());
        }
        if(movie.getViews() != null) {
            this.views = movie.getViews().stream().map(view -> new ViewDto(view)).collect(Collectors.toList());
        }
    }

    
}
