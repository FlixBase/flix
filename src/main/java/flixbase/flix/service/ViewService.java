package flixbase.flix.service;

import flixbase.flix.dto.UserDto;
import flixbase.flix.dto.ViewDto;

public interface ViewService {
    
    ViewDto save(ViewDto viewDto);

    ViewDto saveFav(ViewDto viewDto, UserDto userDto);
}
