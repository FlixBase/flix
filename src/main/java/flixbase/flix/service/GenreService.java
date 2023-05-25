package flixbase.flix.service;

import java.util.List;

import flixbase.flix.dto.GenreDto;

public interface GenreService {
    List<GenreDto> getGenres();
    List<GenreDto> getGenresSortedByMovieNumber();
}
