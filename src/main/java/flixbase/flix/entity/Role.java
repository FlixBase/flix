package flixbase.flix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Getter 
@Setter
@NoArgsConstructor  
@AllArgsConstructor 
@Entity
@Table(name="roles")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, unique=true)
    private String name;

    
    @ManyToMany(mappedBy="roles") 
    @JsonBackReference
    private List<User> users;
    
}
