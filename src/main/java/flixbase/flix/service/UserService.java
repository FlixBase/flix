package flixbase.flix.service;

import java.util.List;

import flixbase.flix.dto.MovieDto;
import flixbase.flix.dto.UserDto;
import flixbase.flix.dto.ViewDto;

public interface UserService {
    
    UserDto save(UserDto userDto);

    UserDto findUserByUsername(String username);

    UserDto findUserByEmail(String email);

    UserDto update(UserDto userDto);

    List<ViewDto> excludeUserReviews(Integer userId, List<ViewDto> movieReviews);

    List<MovieDto> getUserRecomendations(Integer userId);

    List<MovieDto> enrichMoviesWithUserFavorites(List<ViewDto> viewDtos, List<MovieDto> movieDtos);
}
