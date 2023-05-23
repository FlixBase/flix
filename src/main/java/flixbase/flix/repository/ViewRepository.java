package flixbase.flix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import flixbase.flix.entity.View;

public interface ViewRepository extends JpaRepository<View, Integer> {
    
}
