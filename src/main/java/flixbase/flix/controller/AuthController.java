package flixbase.flix.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import flixbase.flix.dto.UserDto;
import flixbase.flix.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthController {
    
    private UserService userService;

    @Autowired 
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/index")
    public String index(Model model, Principal principal) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        
        return principal == null ? "index" : "redirect:/user";
    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return principal == null ? "index" : "redirect:/user";
    }

    @GetMapping("/login")
    public String login(Principal principal) {
        if(principal != null) {
            return "redirect:/user";
        }
        return "login";
    }

    // @GetMapping("/register") 
    // public String registrationForm(Model model) {
    //     UserDto user = new UserDto();
    //     model.addAttribute("user", user);
    //     return "register";
    // }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        UserDto existingUserFound = userService.findUserByUsername(userDto.getUsername());
        if(existingUserFound != null && existingUserFound.getUsername() != null && !existingUserFound.getUsername().isEmpty()) {
            result.rejectValue("username", null, "There is already an account associated with that username.");
        }
        UserDto existingUserWithEmail = userService.findUserByEmail(userDto.getEmail());
        if(existingUserWithEmail != null && existingUserWithEmail.getEmail() != null && !existingUserWithEmail.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account associated with that email address.");
        }
        if(result.hasErrors()) {
            model.addAttribute("user", userDto);
            // return "/register";
            return "/index";
        }
        userService.save(userDto);

        // return "redirect:/register?success";
        return "redirect:/login";
    }
}
