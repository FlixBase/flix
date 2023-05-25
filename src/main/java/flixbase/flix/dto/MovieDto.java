package flixbase.flix.dto;


import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import flixbase.flix.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor  
@AllArgsConstructor 
@JsonInclude(value = Include.NON_NULL)
public class MovieDto {

    private Integer id;

    private String title;

    private String description;

    private String posterUrl;

    private Boolean adult;

    private String releaseDate;

    private Integer csvId;

    private String imdbId;

    private Double voteAverage;

    private Integer voteCount;

    private Double popularity;

    private List<GenreDto> genres;

    private List<ViewDto> views;

    private Boolean favorite;

    private Boolean viewed;

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
        if(movie.getAdult() != null) {
            this.adult = movie.getAdult();
        }
        if(movie.getPosterUrl() != null) {
            this.posterUrl = movie.getPosterUrl();
        }
        if(movie.getReleaseDate() != null) {
            this.releaseDate = movie.getReleaseDate();
        }
        if(movie.getCsvId() != null) {
            this.csvId = movie.getCsvId();
        }
        if(movie.getImdbId() != null) {
            this.imdbId = movie.getImdbId();
        }
        if(movie.getVoteAverage() != null) {
            this.voteAverage = movie.getVoteAverage();
        }
        if(movie.getVoteCount() != null) {
            this.voteCount = movie.getVoteCount();
        }
        if(movie.getPopularity() != null) {
            this.popularity = movie.getPopularity();
        }
        if(movie.getGenres() != null) {
            this.genres = movie.getGenres().stream().map(genre -> new GenreDto(genre)).collect(Collectors.toList());
        }
    }

}
