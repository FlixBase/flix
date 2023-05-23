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

    private Integer userId;

    private Integer movieId;


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
            // this.movie = new MovieDto(view.getMovie());
            this.movieId = view.getMovie().getId();
        }
        if(view.getUser() != null) {
            // this.user = new UserDto(view.getUser());
            this.userId = view.getUser().getId();
        }

    }

}
