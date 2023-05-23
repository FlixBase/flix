package flixbase.flix.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import flixbase.flix.dto.GenreDto;
import flixbase.flix.dto.UserDto;
import flixbase.flix.service.GenreService;
import flixbase.flix.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    
    private UserService userService;
    private GenreService genreService;

    @Autowired
    public UserController(UserService userService, GenreService genreService) {
        this.userService = userService;
        this.genreService = genreService;
    }

    @GetMapping
    public String userDashboard(Model model, Principal principal) {
        UserDto loggedInUser = getPrincipal(principal);
        System.out.println(loggedInUser.getUsername());
        model.addAttribute("user", loggedInUser);
        return "user";
    }

    private UserDto getPrincipal(Principal principal) {
        return userService.findUserByUsername(principal.getName());
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("user") UserDto userDto, BindingResult result, Model model, Principal principal) {
        UserDto loggedInUser = getPrincipal(principal);
        if (loggedInUser.getId() == userDto.getId()) {
            UserDto updatedUserDto = userService.update(userDto);
            model.addAttribute("user", updatedUserDto);
        }
        else {
            throw new AccessDeniedException("User is not authorized to perform this operation");
        }
        return "redirect:/user/profile";
    }

    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal) {
        UserDto loggedInUser = getPrincipal(principal);
        List<GenreDto> genres = genreService.getGenres();
        model.addAttribute("user", loggedInUser);
        model.addAttribute("genres", genres);
        return "user_profile";
    }



}
