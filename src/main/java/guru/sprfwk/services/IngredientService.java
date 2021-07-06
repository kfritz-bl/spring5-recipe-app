package guru.sprfwk.services;

import guru.sprfwk.commands.IngredientCommand;


public interface IngredientService {
	
	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
	
	IngredientCommand saveIngredientCommand(IngredientCommand command);
	
	void deleteById(Long recipeId, Long idToDelete);
}
