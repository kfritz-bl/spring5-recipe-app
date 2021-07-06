package guru.sprfwk.services;

import guru.sprfwk.commands.RecipeCommand;
import guru.sprfwk.domain.Recipe;

import java.util.Set;


public interface RecipeService {
	
	Set<Recipe> getRecipes();
	
	Recipe findById(Long l);
	
	RecipeCommand findCommandById(Long l);
	
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	
	void deleteById(Long idToDelete);
}
