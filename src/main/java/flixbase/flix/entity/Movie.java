package flixbase.flix.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import flixbase.flix.dto.MovieDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor  
@AllArgsConstructor 
@Entity
@Table(name="movies")
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title")
    private String title;

    @Column(name="description",columnDefinition="text")
    private String description;

    @Column(name="poster_url")
    private String posterUrl;

    @Enumerated(EnumType.STRING)
    @Column(name="film_rating") 
    private FilmRating filmRating;

    @Column(name="year")
    private String year;

    @Column(name="actors")
    private String actors;

    @Column(name="tags")
    private String tags;

    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonManagedReference
    @JoinTable(
        name="movie_genre",
        joinColumns={@JoinColumn(name="movie_id", referencedColumnName="id")},
        inverseJoinColumns={@JoinColumn(name="genre_id", referencedColumnName="id")})
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(mappedBy="movie")
    @JsonManagedReference
    private List<View> views = new ArrayList<>();



    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void removeGenre(Genre genre) {
        genres.remove(genre);
    }

    public void addView(View view) {
        views.add(view);
    }

    public void removeView(View view) {
        views.remove(view);
    }

    public Movie(MovieDto movieDto) {
        if(movieDto.getTitle() != null) {
            this.title = movieDto.getTitle();
        }
        if(movieDto.getDescription() != null) {
            this.description = movieDto.getDescription();
        }
        if(movieDto.getPosterUrl() != null) {
            this.posterUrl = movieDto.getPosterUrl();
        }
        if(movieDto.getYear() != null) {
            this.year = movieDto.getYear();
        }
        if(movieDto.getFilmRating() != null) {
            this.filmRating = movieDto.getFilmRating();
        }
        if(movieDto.getActors() != null) {
            this.actors = movieDto.getActors();
        }
        if(movieDto.getTags() != null) {
            this.tags = movieDto.getTags();
        }
    }

}
