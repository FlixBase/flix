package flixbase.flix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import flixbase.flix.entity.Movie;

public interface MovieRepository  extends JpaRepository<Movie, Integer> {

    List<Movie> findAllByOrderByVoteAverage(PageRequest pageRequest);

    List<Movie> findAllByOrderByPopularity(PageRequest pageRequest);

    List<Movie> findByGenres_Id(Integer genreId, PageRequest pageRequest);

    @Query("""
        SELECT m FROM Movie m JOIN m.genres g WHERE g.id IN :favoriteGenreIds 
            AND m.id NOT IN :viewedMovieIds
            GROUP BY m ORDER BY COUNT(g) DESC, m.voteAverage DESC LIMIT :limit
        """)
    List<Movie> getRecomendedExcludeViewed(@Param("favoriteGenreIds") List<Integer> favoriteGenreIds, @Param("limit") Integer limit,  @Param("viewedMovieIds") List<Integer> viewedMovieIds);

    @Query("""
        SELECT m FROM Movie m JOIN m.genres g WHERE g.id IN :favoriteGenreIds GROUP BY m ORDER BY COUNT(g) DESC, m.voteAverage DESC LIMIT :limit
        """)
    List<Movie> getRecomended(@Param("favoriteGenreIds") List<Integer> favoriteGenreIds, @Param("limit") Integer limit);
}
