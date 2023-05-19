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

    @Column(name="adult") 
    private Boolean adult;

    @Column(name="release_date")
    private String releaseDate;

    @Column(name="csv_id")
    private Integer csvId;

    @Column(name="imdb_id")
    private String imdbId;

    @Column(name="vote_avg")
    private Double voteAverage;

    @Column(name="vote_count")
    private Integer voteCount;

    @Column(name="popularity")
    private Double popularity;

    @ManyToMany(fetch=FetchType.EAGER)
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
        if(movieDto.getReleaseDate() != null) {
            this.releaseDate = movieDto.getReleaseDate();
        }
        if(movieDto.getCsvId() != null) {
            this.csvId = movieDto.getCsvId();
        }
        if(movieDto.getImdbId() != null) {
            this.imdbId = movieDto.getImdbId();
        }
        if(movieDto.getVoteAverage() != null) {
            this.voteAverage = movieDto.getVoteAverage();
        }
        if(movieDto.getVoteCount() != null) {
            this.voteCount = movieDto.getVoteCount();
        }
        if(movieDto.getPopularity() != null) {
            this.popularity = movieDto.getPopularity();
        }
        if(movieDto.getAdult() != null) {
            this.adult = movieDto.getAdult();
        }

    }

}
