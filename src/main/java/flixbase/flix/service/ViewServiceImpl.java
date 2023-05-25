package flixbase.flix.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flixbase.flix.dto.UserDto;
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

        System.out.println("rating from Service - save: " + viewDto.getRating());
        System.out.println("View ID from Service - save : " + viewDto.getId());
        
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
        viewDto.setReview(viewDto.getReview().trim());
        View view = new View(viewDto);
        view.setMovie(movie);
        view.setUser(user);
        System.out.println("View id from Service - new View from DTO: " + view.getId());
        View dbView = viewRepository.save(view);
        System.out.println("View id from Service - new View from DB: " + dbView.getId());

        return new ViewDto(dbView);
    }

    @Override
    public ViewDto saveFav(ViewDto viewDto, UserDto userDto) {
        Optional<Movie> movieResult = movieRepository.findById(viewDto.getMovieId());
        Movie movie = null;
        View view = null;
        if(movieResult.isPresent()) {
            movie = movieResult.get();
        } else {
            return null;
        }
        if(viewDto.getId() != null) {
            view = viewRepository.findById(viewDto.getId()).get();
        }
        if(view == null) {
            view = new View();
            view.setMovie(movie);
            view.setUser(userRepository.findById(userDto.getId()).get());
            view.setFavorite(true);
        } else {
            if(view.getFavorite() == null) {
                view.setFavorite(true);
            } else {
                view.setFavorite(!view.getFavorite());
            }
        }   
        System.out.println("view id from service saveFav: " + view.getId());
        return new ViewDto(viewRepository.save(view));
    }

    
}
