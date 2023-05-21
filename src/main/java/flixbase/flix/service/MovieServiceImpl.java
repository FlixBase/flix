package flixbase.flix.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import flixbase.flix.dto.MovieDto;
import flixbase.flix.dto.ViewDto;
import flixbase.flix.entity.Movie;
import flixbase.flix.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Override
    public MovieDto getById(Integer movieId) {
        //TODO: checkifExists
        Movie movie = movieRepository.findById(movieId).get();
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
    public List<MovieDto> getTopByRatedGenre(String genre, Integer topSize) {
        return new ArrayList<>();
    }
    @Override
    public List<MovieDto> getTopByPopularGenre(String genre, Integer topSize) {
        return new ArrayList<>();
    }
    @Override
    public List<MovieDto> getUserRecomendations(Integer userId, Integer topSize) {
        return new ArrayList<>();
    }
    @Override
    public List<MovieDto> enrichMoviesWithFavorites(List<ViewDto> viewDtos, List<MovieDto> movieDtos) {

        for (MovieDto movieDto : movieDtos) {
            boolean isFavorite = viewDtos.stream()
                .filter(viewDto -> viewDto.getMovie().getId().equals(movieDto.getId()))
                .anyMatch(viewDto -> viewDto.getFavorite());

            if (isFavorite) {
                movieDto.setFavorite(true);
            }
        }
        return movieDtos;
    }
    @Override
    public List<MovieDto> enrichMoviesWithViewed(List<ViewDto> viewDtos, List<MovieDto> movieDtos) {
            for (MovieDto movieDto : movieDtos) {
                boolean movieExistsInViews = viewDtos.stream()
                        .anyMatch(viewDto -> viewDto.getMovie().getId().equals(movieDto.getId()));
                if(movieExistsInViews) {
                movieDto.setViewed(true);
                
            }
        }
        return movieDtos;
    }
}
