package flixbase.flix.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flixbase.flix.dto.GenreDto;
import flixbase.flix.entity.Genre;
import flixbase.flix.repository.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    GenreRepository genreRepository;

    @Override
    public List<GenreDto> getGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
            .map(genre -> new GenreDto(genre)).collect(Collectors.toList());
    }

    @Override
    public List<GenreDto> getGenresSortedByMovieNumber() {
        List<Genre> genres = genreRepository.findAll();
        // Sort the genres based on the number of movies in descending order
        List<Genre> sortedGenres = genres.stream()
            .sorted(Comparator.comparingInt(genre -> genre.getMovies().size()))
            .collect(Collectors.toList());

        return sortedGenres.stream()
            .map(genre -> new GenreDto(genre)).collect(Collectors.toList());
    }
}
