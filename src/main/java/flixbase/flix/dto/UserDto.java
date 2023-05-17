package flixbase.flix.dto;

import java.util.List;
import java.util.stream.Collectors;

import flixbase.flix.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor  
@AllArgsConstructor 
public class UserDto {

    private Integer id;

    private String username;

    private String email;
    
    private List<GenreDto> favoriteGenres;

    private List<ViewDto> views;
    


    public UserDto(User user) {
        if(user.getId() != null) {
            this.id = user.getId();
        }
        if(user.getUsername() != null) {
            this.username = user.getUsername();
        }
        if(user.getEmail() != null) {
            this.email = user.getEmail();
        }
        if(user.getFavoriteGenres() != null) {
            this.favoriteGenres = user.getFavoriteGenres().stream().map(genre -> new GenreDto(genre)).collect(Collectors.toList());
        }
        if(user.getViews() != null) {
            this.views = user.getViews().stream().map(view -> new ViewDto(view)).collect(Collectors.toList());
        }
    }

}
