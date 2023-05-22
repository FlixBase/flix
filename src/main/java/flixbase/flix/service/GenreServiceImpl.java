package flixbase.flix.service;

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

    public List<GenreDto> getGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
            .map(genre -> new GenreDto(genre)).collect(Collectors.toList());
    }
}
