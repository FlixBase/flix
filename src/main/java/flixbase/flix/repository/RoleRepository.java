package flixbase.flix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import flixbase.flix.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    Role findByName(String roleName);
}
