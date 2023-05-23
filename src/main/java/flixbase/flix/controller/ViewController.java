package flixbase.flix.controller;

import flixbase.flix.dto.ViewDto;
import flixbase.flix.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {
	private ViewService viewService;
	
	@Autowired
	public ViewController(ViewService viewService) {this.viewService = viewService; }
	
	@GetMapping("/get")
	public ViewDto getView() {
		return new ViewDto();
	}
	
	@PostMapping("/add")
	public String addView(@ModelAttribute("view") ViewDto viewDto) {
		if (viewService.save(viewDto) != null)
			return "{pg-string}";
		else
			return "";
	}
}
