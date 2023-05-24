package flixbase.flix.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import flixbase.flix.dto.UserDto;
import flixbase.flix.entity.Role;
import flixbase.flix.entity.User;
import flixbase.flix.repository.GenreRepository;
import flixbase.flix.repository.RoleRepository;
import flixbase.flix.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private GenreRepository genreRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, MovieService movieService, GenreRepository genreRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.genreRepository = genreRepository;
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

        User user = userRepository.findById(userDto.getId()).get();

        if(userDto.getGenreIds() != null) {
            user.getFavoriteGenres().clear();
            for(Integer genreId : userDto.getGenreIds()) {
                user.addGenre(genreRepository.findById(genreId).get());
            }
        }

        return new UserDto(userRepository.save(user));
    }

    private Role settingDefaultRoleName() {
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }

}
