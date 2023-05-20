package flixbase.flix.dto;

import java.util.List;

import flixbase.flix.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor  
@AllArgsConstructor 
public class RoleDto {
    
    private Integer id;

    private String name;

    private List<UserDto> users;


    public RoleDto(Role role) {
        if(role.getId() != null) {
            this.id = role.getId();
        }
        if(role.getName() != null) {
            this.name = role.getName();
        }
    }
}
