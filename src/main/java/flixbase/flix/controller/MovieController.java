package flixbase.flix.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
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
            if(view.getMovieId() == movieId) {
                userMovieReview = view;
            }
        }
       
        if(userMovieReview.getId() == null) {
            userMovieReview.setUserId(userDto.getId());
            userMovieReview.setMovieId(movieId);
        }
        List<ViewDto> movieReviews = movieService.getReviews(movieId);
        List<MovieDto> otherMovies = movieService.getTopByRatedGenre(movieDto.getGenres().get(0).getId(),3);
        model.addAttribute("movie", movieDto);
        model.addAttribute("userReview", userMovieReview);
        model.addAttribute("reviews", movieReviews);
        model.addAttribute("user", userDto);
        model.addAttribute("otherMovies", otherMovies);
        return "movie";
    }

    @GetMapping({"/getTopRated"})
    public String getTopRated(@RequestParam("size") int size, Model model, Principal principal) {
        List<MovieDto> movies = movieService.getTopRated(size);
        UserDto userDto = getLoggedInUser(principal);
        List<GenreDto> genres = genreService.getGenres();
        movieService.enrichMoviesWithFavorites(userDto.getViews(), movies);
        movieService.enrichMoviesWithViewed(userDto.getViews(), movies);
        model.addAttribute("user", getLoggedInUser(principal));
        model.addAttribute("movies", movies);
        model.addAttribute("genres", genres);
        return "movies";
    }

    @GetMapping({"/getTopRatedByGenre"})
    public String getTopRatedByGenre(
            @RequestParam("genreId") int genreId, 
            @RequestParam("size") int size, 
            Model model, 
            Principal principal) {
        List<MovieDto> movies = movieService.getTopByRatedGenre(genreId, size);
        UserDto userDto = getLoggedInUser(principal);
        List<GenreDto> genres = genreService.getGenres();
        movieService.enrichMoviesWithFavorites(userDto.getViews(), movies);
        movieService.enrichMoviesWithViewed(userDto.getViews(), movies);
        model.addAttribute("user", getLoggedInUser(principal));
        model.addAttribute("movies", movies);
        model.addAttribute("genres", genres);
        return "movies";
    }

    @GetMapping({"/getTopRatedByAllGenres"})
    public String getTopRatedByAllGenres (@RequestParam("size") int size, Model model, Principal principal) {
        UserDto userDto = getLoggedInUser(principal);
        List<GenreDto> genreDtos = genreService.getGenresSortedByMovieNumber();
        Hashtable <String, List<MovieDto>> genresMovies = new Hashtable<>(); 
        for (GenreDto genreDto : genreDtos) {
            List<MovieDto> movies = movieService.getTopByRatedGenre(genreDto.getId(), size);
            movieService.enrichMoviesWithFavorites(userDto.getViews(), movies);
            movieService.enrichMoviesWithViewed(userDto.getViews(), movies);
            genresMovies.put(genreDto.getName(), movies);
        }
        model.addAttribute("user", getLoggedInUser(principal));
        model.addAttribute("genresMovies", genresMovies);
        return "movie";
    }

    @GetMapping({"/getTopRatedByUserGenres"})
    public String getTopRatedByUserGenres(
            @RequestParam("genresNumber") int genresNumber, 
            @RequestParam("moviesPerGenre") int moviesPerGenre, 
            Model model, 
            Principal principal) {
                
        Hashtable <String, List<MovieDto>> genresMovies = new Hashtable<>(); 
        UserDto userDto = getLoggedInUser(principal);
        List<GenreDto> userGenreDtos = userDto.getFavoriteGenres();

        for (GenreDto genreDto : userGenreDtos) {
            List<MovieDto> movies = movieService.getTopByRatedGenre(genreDto.getId(), moviesPerGenre);
            movieService.enrichMoviesWithFavorites(userDto.getViews(), movies);
            movieService.enrichMoviesWithViewed(userDto.getViews(), movies);
            genresMovies.put(genreDto.getName(), movies);
        }

        if (userGenreDtos.size() < genresNumber) {
            List<GenreDto> allGenreDtos = genreService.getGenresSortedByMovieNumber();
            int genresToAddNumber = genresNumber - userGenreDtos.size();
            for (GenreDto genreDto : allGenreDtos) {
                if (!userGenreDtos.contains(genreDto) && genresToAddNumber > 0) {
                    List<MovieDto> movies = movieService.getTopByRatedGenre(genreDto.getId(), moviesPerGenre);
                    movieService.enrichMoviesWithFavorites(userDto.getViews(), movies);
                    movieService.enrichMoviesWithViewed(userDto.getViews(), movies);
                    genresMovies.put(genreDto.getName(), movies);
                    genresToAddNumber--;
                }
            }
        }
        HashMap<String, Integer> userStat = new HashMap<>();
        userStat.put("favorites", 0);
        userStat.put("ratings", 0);
        userStat.put("reviews", 0);
        for(ViewDto viewDto : getLoggedInUser(principal).getViews()) {
            if(viewDto.getFavorite() != null && viewDto.getFavorite()) userStat.put("favorites", userStat.get("favorites") + 1);
            if(viewDto.getRating() != null && viewDto.getRating() != 0) userStat.put("ratings", userStat.get("ratings") + 1);
            if(viewDto.getReview() != null && viewDto.getReview().length() > 0) userStat.put("reviews", userStat.get("reviews") + 1);
        }
        model.addAttribute("user", getLoggedInUser(principal));
        model.addAttribute("genresMovies", genresMovies);
        model.addAttribute("userStat", userStat);
        return "user";
    }

    @GetMapping({"/getFavorites"})
    public String getFavorites(Model model, Principal principal) {
        UserDto userDto = getLoggedInUser(principal);
        List<MovieDto> movies = new ArrayList<>();

        for (ViewDto viewDto : userDto.getViews()) {
            if (viewDto.getFavorite() != null && viewDto.getFavorite() != false) {
                movies.add(viewDto.getMovie());
            }
        }
        model.addAttribute("user", userDto);
        model.addAttribute("movies", movies);
        return "favorites";
    }

    @GetMapping({"/getViewed"})
    public String getViewed(Model model, Principal principal) {
        UserDto userDto = getLoggedInUser(principal);
        List<MovieDto> movies = userDto.getViews().stream()
            .map(view -> view.getMovie()).collect(Collectors.toList());
        movieService.enrichMoviesWithFavorites(userDto.getViews(), movies);
        model.addAttribute("user", userDto);
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping({"/getRecomended"})
    public String getRecomended(@RequestParam("size") int size, Model model, Principal principal) {
        UserDto userDto = getLoggedInUser(principal);
        List<MovieDto> movies = null;
        if (userDto.getFavoriteGenres().size() > 0){
            movies = movieService.getRecomended(userDto.getFavoriteGenres(), size, userDto.getViews());
        }
        model.addAttribute("user", userDto);
        model.addAttribute("movies", movies);
        return "recommendation";
    }

    private UserDto getLoggedInUser(Principal principal) {
        return userService.findUserByUsername(principal.getName());
    }

}
