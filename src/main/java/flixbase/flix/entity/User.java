package flixbase.flix.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import flixbase.flix.dto.UserDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="username", nullable=false, unique=true) 
    private String username;

    @Column(name="email", unique=true) 
    private String email;

    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonManagedReference
    @JoinTable(
        name="user_genre",
        joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
        inverseJoinColumns={@JoinColumn(name="genre_id", referencedColumnName="id")})
    private List<Genre> favoriteGenres = new ArrayList<>();

    @OneToMany(mappedBy="user")
    @JsonManagedReference
    private List<View> views = new ArrayList<>();
    

    public void addGenre(Genre genre) {
        favoriteGenres.add(genre);
    }

    public void removeGenre(Genre genre) {
        favoriteGenres.remove(genre);
    }

    public void addView(View view) {
        views.add(view);
    }

    public void removeView(View view) {
        views.remove(view);
    }

    public User(UserDto userDto) {
        if(userDto.getEmail() != null) {
            this.email = userDto.getEmail();
        }
        if(userDto.getUsername() != null) {
            this.username = userDto.getUsername();
        }
    }

    
}
