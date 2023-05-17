package flixbase.flix.dto;


import java.util.List;


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
            // map genres to genreDtos
        }
        if(movie.getViews() != null) {
            // map views to viewDtos
        }
    }

    
}
