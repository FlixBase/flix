package flixbase.flix.dto;

import java.util.List;

import flixbase.flix.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor  
@AllArgsConstructor 
public class GenreDto {
    

    private Integer id;

    private String name;

    private List<MovieDto> movies;

    private List<UserDto> users;


    
    public GenreDto(Genre genre) {
        
        if(genre.getId() != null) {
            this.id = genre.getId();
        }
        if(genre.getName() != null) {
            this.name = genre.getName();
        }
        if(genre.getUsers() != null) {
            // map users to userDtos
        }
        if(genre.getMovies() != null) {
            // map movie to movieDtos
        }
    }

}
