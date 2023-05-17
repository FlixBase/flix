package flixbase.flix.entity;

import java.util.List;

import flixbase.flix.dto.GenreDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name="genres")
public class Genre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy="genres")
    private List<Movie> movies;

    @ManyToMany(mappedBy="favoriteGenres")
    private List<User> users;

    public Genre(GenreDto genreDto) {
        if(genreDto.getName() != null) {
            this.name = genreDto.getName();
        }
    }
    
   

}
