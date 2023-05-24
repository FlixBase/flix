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

    
    // not including user and movie as that need to be handled in service layer
    public View(ViewDto viewDto) {
      if(viewDto.getId() != null) {
        this.id = viewDto.getId();
      }
      if(viewDto.getReview() != null) {
        this.review = viewDto.getReview();
      }
      if(viewDto.getRating() != null) {
        this.rating = viewDto.getRating();
      }
      if(viewDto.getFavorite() != null) {
        this.favorite = viewDto.getFavorite();
      }
    }

  }
