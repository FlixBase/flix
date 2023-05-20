package flixbase.flix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import flixbase.flix.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByUsername(String username);
}
