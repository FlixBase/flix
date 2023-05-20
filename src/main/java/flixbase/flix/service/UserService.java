package flixbase.flix.service;

import flixbase.flix.dto.UserDto;

public interface UserService {
    
    UserDto save(UserDto userDto);

    UserDto findUserByUsername(String username);

    
    
}
