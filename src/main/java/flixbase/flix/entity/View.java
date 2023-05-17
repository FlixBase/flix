package flixbase.flix.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import flixbase.flix.dto.ViewDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="views")
public class View {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="review", columnDefinition="text")
    private String review;

    @Column(name="rating")
    private Integer rating;

    @Column(name="favorite", columnDefinition = "boolean default false")
    private Boolean favorite;

    @ManyToOne
    @JsonBackReference  
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="movie_id")
    private Movie movie;


    public View(ViewDto viewDto) {
      if(viewDto.getReview() != null) {
        this.review = viewDto.getReview();
      }
      if(viewDto.getRating() != null) {
        this.rating = viewDto.getRating();
      }
      if(viewDto.getFavorite() != null) {
        this.favorite = viewDto.getFavorite();
      }
      if(viewDto.getMovie() != null) {
        // map movieDto to movie
      }
      if(viewDto.getUser() != null) {
        // map userDto to user
      }
    }

  }
