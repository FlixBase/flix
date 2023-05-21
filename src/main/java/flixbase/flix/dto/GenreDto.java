package flixbase.flix.dto;

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

    public GenreDto(Genre genre) {
        
        if(genre.getId() != null) {
            this.id = genre.getId();
        }
        if(genre.getName() != null) {
            this.name = genre.getName();
        }
    }

}
