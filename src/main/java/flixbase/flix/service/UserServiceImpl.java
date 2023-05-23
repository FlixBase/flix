package flixbase.flix.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import flixbase.flix.dto.MovieDto;
import flixbase.flix.dto.UserDto;
import flixbase.flix.dto.ViewDto;
import flixbase.flix.entity.Role;
import flixbase.flix.entity.User;
import flixbase.flix.repository.RoleRepository;
import flixbase.flix.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private MovieService movieService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, MovieService movieService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = new User(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("USER");
        if(role == null) {
            role = settingDefaultRoleName();
        }
        user.setRoles(Arrays.asList(role));
        return new UserDto(userRepository.save(user));
    }

    @Override
    public UserDto findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            return new UserDto(user);
        }
       return null;
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            return new UserDto(user);
        }
        return null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = new User(userDto);
        return new UserDto(userRepository.save(user));
    }

    @Override
    public List<MovieDto> getUserRecomendations(Integer userId) {
        //TODO: get real UsercRecomendations instead of stub
        return movieService.getTopPopular(10);
    }

    private Role settingDefaultRoleName() {
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }

    @Override
    public List<ViewDto> excludeUserReviews(Integer userId, List<ViewDto> movieReviews) {
        Iterator<ViewDto> iterator = movieReviews.iterator();
        while (iterator.hasNext()) {
            ViewDto review = iterator.next();
            if (review.getUserId() == userId) {
                iterator.remove();
            }
        }
        return movieReviews;
    }

}
