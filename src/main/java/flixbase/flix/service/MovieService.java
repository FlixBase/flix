package flixbase.flix.service;

import java.util.List;

import flixbase.flix.dto.GenreDto;
import flixbase.flix.dto.MovieDto;
import flixbase.flix.dto.ViewDto;

public interface MovieService {

    MovieDto getById(Integer movieId);

    List<MovieDto> getTopRated(Integer topSize);

    List<MovieDto> getTopPopular(Integer topSize);

    List<MovieDto> getTopByRatedGenre(Integer genreId, Integer topSize);

    List<MovieDto> getTopByPopularGenre(Integer genreId, Integer topSize);

    List<MovieDto> enrichMoviesWithFavorites(List<ViewDto> viewDtos, List<MovieDto> movieDtos);

    List<MovieDto> enrichMoviesWithViewed(List<ViewDto> viewDtos, List<MovieDto> movieDtos);

    List<ViewDto> getReviews(Integer movieId);

    List<MovieDto> getRecomended(List<GenreDto> favoriteGenres, Integer limit, List<ViewDto> userViews);
}
