package flixbase.flix.rest;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flixbase.flix.dto.UserDto;
import flixbase.flix.dto.ViewDto;
import flixbase.flix.service.UserService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    UserService userService;

    @GetMapping("/getViews")
    public ResponseEntity<List<ViewDto>> getViews(Principal principal) {
        UserDto user = getPrincipal(principal);
        return ResponseEntity.ok(user.getViews());
    }

    private UserDto getPrincipal(Principal principal) {
        return userService.findUserByUsername(principal.getName());
    }
}
