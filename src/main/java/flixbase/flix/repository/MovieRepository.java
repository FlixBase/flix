package flixbase.flix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import flixbase.flix.entity.Movie;

public interface MovieRepository  extends JpaRepository<Movie, Integer> {
    List<Movie> findAllByOrderByVoteAverage(PageRequest pageRequest);

    List<Movie> findAllByOrderByPopularity(PageRequest pageRequest);
}
