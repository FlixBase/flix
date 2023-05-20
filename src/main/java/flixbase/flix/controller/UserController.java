package flixbase.flix.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import flixbase.flix.dto.UserDto;
import flixbase.flix.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
}
