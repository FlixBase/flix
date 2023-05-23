package flixbase.flix.service;

import flixbase.flix.dto.ViewDto;
import flixbase.flix.entity.User;
import flixbase.flix.entity.View;
import flixbase.flix.repository.MovieRepository;
import flixbase.flix.repository.UserRepository;
import flixbase.flix.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ViewServiceImpl implements ViewService {
	ViewRepository viewRepository;
	UserRepository userRepository;
	MovieRepository movieRepository;
	
	@Autowired
	public ViewServiceImpl(ViewRepository viewRepository,
	                       UserRepository userRepository,
	                       MovieRepository movieRepository) {
		this.viewRepository = viewRepository;
		this.userRepository = userRepository;
		this.movieRepository = movieRepository;
	}
	
	@Override
	public ViewDto save(ViewDto viewDto) {
		if (userRepository.findById(viewDto.getUser().getId()).isEmpty())
			return null;
		if (movieRepository.findById(viewDto.getMovie().getId()).isEmpty())
			return null;
		
		viewRepository.save(new View(viewDto));
		
		return viewDto;
	}
}
