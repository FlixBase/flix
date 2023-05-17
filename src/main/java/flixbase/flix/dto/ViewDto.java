package flixbase.flix.dto;

import flixbase.flix.entity.View;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor  
@AllArgsConstructor 
public class ViewDto {
    
    private Integer id;

    private String review;

    private Integer rating;

    private Boolean favorite;

    private UserDto user;

    private MovieDto movie;



    public ViewDto(View view) {
        if(view.getId() != null) {
            this.id = view.getId();
        }
        if(view.getReview() != null) {
            this.review = view.getReview();
        }
        if(view.getRating() != null) {
            this.rating = view.getRating();
        }
        if(view.getFavorite() != null) {
            this.favorite = view.getFavorite();
        }
        if(view.getMovie() != null) {
            // map movie to movieDto
        }
        if(view.getUser() != null) {
            // map user to userDto
        }

    }

}
