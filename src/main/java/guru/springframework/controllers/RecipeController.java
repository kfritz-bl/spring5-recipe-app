package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Slf4j
@Controller
public class RecipeController {
	
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
	private final RecipeService recipeSvc;
	
	public RecipeController(RecipeService recipeSvc) {
		this.recipeSvc = recipeSvc;
	}
	
	@GetMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeSvc.findById(new Long(id)));
		
		return "recipe/show";
	}
	
	@GetMapping("recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		
		return "recipe/recipeform";
	}
	
	@GetMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeSvc.findCommandById(Long.valueOf(id)));
		return RECIPE_RECIPEFORM_URL;
	}
	
	@PostMapping("recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand cmd, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
			
			return RECIPE_RECIPEFORM_URL;
		}
		
		RecipeCommand savedCmd = recipeSvc.saveRecipeCommand(cmd);
		
		return String.format("redirect:/recipe/%d/show", savedCmd.getId());
	}
	
	@GetMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		log.debug("Deleting id: " + id);
		
		recipeSvc.deleteById(Long.valueOf(id));
		return "redirect:/";
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		log.error("Handling Not Found Exception: " + exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setStatus(HttpStatus.NOT_FOUND);
		modelAndView.setViewName("error");
		modelAndView.addObject("exception", exception);
		modelAndView.addObject("status", 404);
		modelAndView.addObject("titleMsg", "404 Not Found Error");
		
		return modelAndView;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNumberFormat(Exception exception) {
		log.error("Handling Number Format Exception: " + exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setStatus(HttpStatus.BAD_REQUEST);
		modelAndView.setViewName("error");
		modelAndView.addObject("exception", exception);
		modelAndView.addObject("status", 400);
		modelAndView.addObject("titleMsg", "400 Bad Request Error");
		
		return modelAndView;
	}
}
