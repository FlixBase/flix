package flixbase.flix.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import flixbase.flix.dto.GenreDto;
import flixbase.flix.dto.MovieDto;
import flixbase.flix.dto.UserDto;
import flixbase.flix.dto.ViewDto;
import flixbase.flix.service.GenreService;
import flixbase.flix.service.MovieService;
import flixbase.flix.service.UserService;

@Controller
@RequestMapping("/movies")
public class MovieController {
    
    private MovieService movieService;
    private UserService userService;
    private GenreService genreService;

    @Autowired
    public MovieController(MovieService movieService, UserService userService, GenreService genreService) {
        this.movieService = movieService;
        this.userService = userService;
        this.genreService = genreService;
    }

    @GetMapping({"/getMovieById"})
    public String getMovieById(@RequestParam("movieId") int movieId, Model model, Principal principal) {
        UserDto userDto = getLoggedInUser(principal);
        MovieDto movieDto = movieService.getById(movieId);
        ViewDto userMovieReview = new ViewDto();

        for(ViewDto view : userDto.getViews()) {
            if (view.getMovie().getId() == movieId && !view.getReview().isEmpty()) {
                userMovieReview = view;
            }
        }

        // if sending over empty dto, preset user and movie id
        if(userMovieReview.getId() == null) {
            userMovieReview.setUserId(userDto.getId());
            userMovieReview.setMovieId(movieId);
        }
        
        List<ViewDto> movieReviews = movieService.getReviews(movieId);

        model.addAttribute("movie", movieDto);
        model.addAttribute("userReview", userMovieReview);
        model.addAttribute("reviews", movieReviews);
        model.addAttribute("user", userDto);

        return "movie";
    }

    @GetMapping({"/getTopRated"})
    public String getTopRated(@RequestParam("size") int size, Model model, Principal principal) {
        List<MovieDto> movies = movieService.getTopRated(size);
        model.addAttribute("movies", movies);
        return "movie";
    }

    @GetMapping({"/getTopRatedByGenre"})
    public String getTopRatedByGenre(
            @RequestParam("genreId") int genreId, 
            @RequestParam("size") int size, 
            Model model, 
            Principal principal) {

        List<MovieDto> movies = movieService.getTopByRatedGenre(genreId, size);

        model.addAttribute("user", getLoggedInUser(principal));
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping({"/getTopRatedByAllGenres"})
    public String getTopRatedByAllGenres (@RequestParam("size") int size, Model model, Principal principal) {
        List<GenreDto> genreDtos = genreService.getGenres();
        Hashtable <String, List<MovieDto>> genresMovies = new Hashtable<>(); 

        for (GenreDto genreDto : genreDtos) {
            genresMovies.put(genreDto.getName(), movieService.getTopByRatedGenre(genreDto.getId(), size));
        }

        model.addAttribute("genresMovies", genresMovies);
        return "movie";
    }

    @GetMapping({"/getFavorites"})
    public String getFavorites(Model model, Principal principal) {
        UserDto userDto = getLoggedInUser(principal);
        List<MovieDto> movies = new ArrayList<>();

        for (ViewDto viewDto : userDto.getViews()) {

            if (viewDto.getFavorite()) {
                movies.add(viewDto.getMovie());
            }
        }
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping({"/getViewed"})
    public String getViewed(Model model, Principal principal) {
        UserDto userDto = getLoggedInUser(principal);
        List<MovieDto> movies = userDto.getViews().stream()
            .map(view -> view.getMovie()).collect(Collectors.toList());
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping({"/getRecomended"})
    public String getRecomended(Model model, Principal principal) {
        UserDto userDto = getLoggedInUser(principal);
        List<MovieDto> movies = userDto.getViews().stream()
            .map(view -> view.getMovie()).collect(Collectors.toList());
        model.addAttribute("movies", movies);
        return "movies";
    }

    private UserDto getLoggedInUser(Principal principal) {
        return userService.findUserByUsername(principal.getName());
    }

}
