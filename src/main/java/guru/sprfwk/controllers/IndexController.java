package guru.sprfwk.controllers;

import guru.sprfwk.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class IndexController {
	
	private final RecipeService recipeSvc;
	
	public IndexController(RecipeService recipeSvc) {
		this.recipeSvc = recipeSvc;
	}
	
	@RequestMapping({"", "/", "/index"})
	public String getIndexPage(Model model) {
		log.debug("Getting Index page");
		model.addAttribute("recipes", recipeSvc.getRecipes());
		
		return "index";
	}
}
