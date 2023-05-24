package flixbase.flix.controller;

import java.security.Principal;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import flixbase.flix.dto.UserDto;
import flixbase.flix.dto.ViewDto;
import flixbase.flix.service.UserService;
import flixbase.flix.service.ViewService;

@Controller
@RequestMapping("/view")
public class ViewController {
    
    private ViewService viewService;
    private UserService userService;
    
    @Autowired
    public ViewController(ViewService viewService, UserService userService) {
        this.viewService = viewService;
        this.userService = userService;
    }

    @GetMapping
    public String singleViewForm(Model model, Principal principal) {
        model.addAttribute("user", getPrincipal(principal));
        model.addAttribute("view", new ViewDto());
        return "view";
    }

    @PostMapping("/addView")
    public String addView(@Valid @ModelAttribute("view") ViewDto viewDto, BindingResult result, Principal principal, Model model) {
        
        
        UserDto user = getPrincipal(principal);
        viewDto.setUserId(user.getId());
        ViewDto dbView = viewService.save(viewDto);
        model.addAttribute("user", user);
        if(dbView == null) {
            result.rejectValue("movieId", null, "Movie id not valid");
            model.addAttribute("message", "Movie id not valid, this is caused by not having a valid movie id or you have already left a review for this movie.");
            model.addAttribute("view", viewDto);
            return "view";
        }

        if(result.hasErrors()) {
            model.addAttribute("message", "Some error happened");
            model.addAttribute("view", viewDto);
            return "view";
        }

        model.addAttribute("message", "Success! Add another one.");
        viewDto = new ViewDto();
        model.addAttribute("view", new ViewDto());
        return "view";
    }


    private UserDto getPrincipal(Principal principal) {
        return userService.findUserByUsername(principal.getName());
    }
}
