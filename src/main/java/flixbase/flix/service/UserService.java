package flixbase.flix.service;

import flixbase.flix.dto.UserDto;

public interface UserService {
    
    UserDto save(UserDto userDto);

    UserDto findUserByUsername(String username);

    UserDto findUserByEmail(String email);

    UserDto update(UserDto userDto);
}
