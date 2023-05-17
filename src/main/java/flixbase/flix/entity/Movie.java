package flixbase.flix.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="description",columnDefinition="text")
    private String description;

    @Column(name="poster_url")
    private String posterUrl;

    @Column(name="film_rating")
    private String filmRating;

    @Column(name="year")
    private String year;

    @Column(name="actors")
    private String actors;

    @Column(name="tags")
    private String tags;

    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
        name="movie_genre",
        joinColumns={@JoinColumn(name="movie_id", referencedColumnName="id")},
        inverseJoinColumns={@JoinColumn(name="genre_id", referencedColumnName="id")})
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(mappedBy="movie")
    @JsonManagedReference
    private List<View> views = new ArrayList<>();

}
