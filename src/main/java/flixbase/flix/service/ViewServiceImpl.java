package flixbase.flix.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flixbase.flix.dto.ViewDto;
import flixbase.flix.entity.Movie;

import flixbase.flix.entity.User;
import flixbase.flix.entity.View;
import flixbase.flix.repository.MovieRepository;
import flixbase.flix.repository.UserRepository;
import flixbase.flix.repository.ViewRepository;

@Service
public class ViewServiceImpl implements ViewService{
    
    private ViewRepository viewRepository;
    private MovieRepository movieRepository;
    private UserRepository userRepository;
    
    
    @Autowired
    public ViewServiceImpl(ViewRepository viewRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.viewRepository = viewRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ViewDto save(ViewDto viewDto) {
        Optional<Movie> movieResult = movieRepository.findById(viewDto.getMovieId());
        Optional<User> userResult = userRepository.findById(viewDto.getUserId());
        Movie movie = null;
        User user = null;
        if(movieResult.isPresent() && userResult.isPresent()) {
            movie = movieResult.get();
            user = userResult.get();
        } else {
            return null;
        }
        View view = new View(viewDto);
        view.setMovie(movie);
        view.setUser(user);
        return new ViewDto(viewRepository.save(view));
    }
}
