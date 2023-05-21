package flixbase.flix.service;

import java.util.List;

import flixbase.flix.dto.MovieDto;
import flixbase.flix.dto.ViewDto;

public interface MovieService {

    MovieDto getById(Integer movieId);

    List<MovieDto> getTopRated(Integer topSize);
    List<MovieDto> getTopPopular(Integer topSize);

    List<MovieDto> getTopByRatedGenre(String genre, Integer topSize);
    List<MovieDto> getTopByPopularGenre(String genre, Integer topSize);
    List<MovieDto> getUserRecomendations(Integer userId, Integer topSize);

    List<MovieDto> enrichMoviesWithFavorites(List<ViewDto> viewDtos, List<MovieDto> movieDtos);
    List<MovieDto> enrichMoviesWithViewed(List<ViewDto> viewDtos, List<MovieDto> movieDtos);
}
