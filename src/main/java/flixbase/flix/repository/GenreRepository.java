package flixbase.flix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import flixbase.flix.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findAll();
}
