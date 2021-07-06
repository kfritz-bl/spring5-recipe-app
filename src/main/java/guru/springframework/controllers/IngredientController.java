package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
public class IngredientController {
	
	private final IngredientService ingSvc;
	private final RecipeService recipeSvc;
	private final UnitOfMeasureService uomSvc;
	
	public IngredientController(IngredientService ingSvc,
								RecipeService recipeSvc,
								UnitOfMeasureService uomSvc) {
		this.ingSvc = ingSvc;
		this.recipeSvc = recipeSvc;
		this.uomSvc = uomSvc;
	}
	
	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("Getting ingredient list for recipe id: " + recipeId);
		
		// use command object to avoid lazy load errors in Thymeleaf.
		model.addAttribute("recipe", recipeSvc.findCommandById(Long.valueOf(recipeId)));
		
		return "recipe/ingredient/list";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId,
									   @PathVariable String id,
									   Model model) {
		log.debug("Getting ingredient id " + id + " for recipe id " + recipeId);
		model.addAttribute("ingredient", ingSvc.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		
		return "recipe/ingredient/show";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/new")
	public String newRecipeIngredient(@PathVariable String recipeId, Model model) {
		log.debug("Adding an ingredient to recipe id " + recipeId);
		
		//make sure we have a good id value
		RecipeCommand recipeCmd = recipeSvc.findCommandById(Long.valueOf(recipeId));
		
		//todo raise exception if null
		if(recipeCmd == null) {
			log.debug("The recipe is null.");
		}
		
		//need to return back parent id for hidden form property
		IngredientCommand cmd = new IngredientCommand();
		cmd.setRecipeId(Long.valueOf(recipeId));
		model.addAttribute("ingredient", cmd);
		
		//init uom
		cmd.setUom(new UnitOfMeasureCommand());
		
		model.addAttribute("uomList", uomSvc.listAllUoms());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId,
										 @PathVariable String id,
										 Model model) {
		log.debug("Updating ingredient id " + id + " for recipe id " + recipeId);
		model.addAttribute("ingredient", ingSvc.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		model.addAttribute("uomList", uomSvc.listAllUoms());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand cmd) {
		log.debug("Calling save or update on ingredient id " + cmd.getId() + " for recipe id " + cmd.getRecipeId());
		
		IngredientCommand savedCmd = ingSvc.saveIngredientCommand(cmd);
		
		log.debug("Saved recipe id: " + savedCmd.getRecipeId());
		log.debug("Saved ingredient id: " + savedCmd.getId());
		
		return String.format("redirect:/recipe/%d/ingredient/%d/show", savedCmd.getRecipeId(), savedCmd.getId());
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteIngredient(@PathVariable String recipeId,
								   @PathVariable String id) {
		log.debug("deleting ingredient id:" + id);
		ingSvc.deleteById(Long.valueOf(recipeId), Long.valueOf(id));
		
		return String.format("redirect:/recipe/%s/ingredients", recipeId);
	}
}
