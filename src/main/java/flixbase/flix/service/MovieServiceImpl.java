package flixbase.flix.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import flixbase.flix.dto.GenreDto;
import flixbase.flix.dto.MovieDto;
import flixbase.flix.dto.ViewDto;
import flixbase.flix.entity.Movie;
import flixbase.flix.entity.View;
import flixbase.flix.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Override
    public MovieDto getById(Integer movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return new MovieDto();
        }
        return new MovieDto(movie);
    }
    @Override
    public List<MovieDto> getTopRated(Integer topSize) {
        PageRequest pageRequest = PageRequest.of(0, topSize, Sort.by(Sort.Order.desc("voteAverage")));
        Page<Movie> page = movieRepository.findAll(pageRequest);
        List<Movie> movies = page.getContent();

        return movies.stream()
            .map(movie -> new MovieDto(movie)).collect(Collectors.toList());
    }
    
    @Override
    public List<MovieDto> getTopPopular(Integer topSize) {
        PageRequest pageRequest = PageRequest.of(0, topSize, Sort.by(Sort.Order.desc("popularity")));
        Page<Movie> page = movieRepository.findAll(pageRequest);
        List<Movie> movies = page.getContent();

        return movies.stream()
            .map(movie -> new MovieDto(movie)).collect(Collectors.toList());
    }
    @Override
    public List<MovieDto> getTopByRatedGenre(Integer genreId, Integer topSize) {
        PageRequest pageRequest = PageRequest.of(0, topSize, Sort.by(Sort.Order.desc("voteAverage")));
        List<Movie> movies = movieRepository.findByGenres_Id(genreId , pageRequest);

        return movies.stream()
            .map(movie -> new MovieDto(movie)).collect(Collectors.toList());
    }
    @Override
    public List<MovieDto> getTopByPopularGenre(Integer genreId, Integer topSize) {
        PageRequest pageRequest = PageRequest.of(0, topSize, Sort.by(Sort.Order.desc("popularity")));
        List<Movie> movies = movieRepository.findByGenres_Id(genreId , pageRequest);
        return movies.stream()
            .map(movie -> new MovieDto(movie)).collect(Collectors.toList());
    }
    @Override
    public List<ViewDto> getReviews(Integer movieId) {
        // TODO: add filter by review and rating OR if only has Text Review
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return new ArrayList<>();
        }
        List<View> views = movie.getViews();
        return views.stream().map(view -> new ViewDto(view)).collect(Collectors.toList());
    }
    @Override
    public List<MovieDto> enrichMoviesWithFavorites(List<ViewDto> viewDtos, List<MovieDto> movieDtos) {

        for (MovieDto movieDto : movieDtos) {
            boolean isFavorite = viewDtos.stream()
                .filter(viewDto -> viewDto.getMovie().getId().equals(movieDto.getId()))
                .anyMatch(viewDto -> viewDto.getFavorite() != null && viewDto.getFavorite());

            movieDto.setFavorite(isFavorite);
        }
        return movieDtos;
    }
    @Override
    public List<MovieDto> enrichMoviesWithViewed(List<ViewDto> viewDtos, List<MovieDto> movieDtos) {
            for (MovieDto movieDto : movieDtos) {
                boolean movieExistsInViews = viewDtos.stream()
                        .anyMatch(viewDto -> viewDto.getMovie().getId().equals(movieDto.getId()));
                movieDto.setViewed(movieExistsInViews);
        }
        return movieDtos;
    }
    @Override
    public List<MovieDto> getRecomended(List<GenreDto> favoriteGenres, Integer limit, List<ViewDto> userViews) {

        List<Integer> favoriteGenreIds =  favoriteGenres.stream()
                .map(genre -> genre.getId()).collect(Collectors.toList());
        List<Integer> viewedMovieIds =  userViews.stream()
                .map(view -> view.getMovieId()).collect(Collectors.toList());
        List<Movie> movies = movieRepository.getRecomended(favoriteGenreIds, limit, viewedMovieIds);

        return movies.stream()
            .map(movie -> new MovieDto(movie)).collect(Collectors.toList());
    }
}
