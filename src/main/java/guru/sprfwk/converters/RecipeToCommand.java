package guru.sprfwk.converters;

import guru.sprfwk.commands.RecipeCommand;
import guru.sprfwk.domain.Category;
import guru.sprfwk.domain.Recipe;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class RecipeToCommand implements Converter<Recipe, RecipeCommand> {
	
	private final CategoryToCommand categoryToCmd;
	private final IngredientToCommand ingToCmd;
	private final NotesToCommand notesToCmd;
	
	public RecipeToCommand(CategoryToCommand categoryToCmd,
						   IngredientToCommand ingToCmd,
						   NotesToCommand notesToCmd) {
		this.categoryToCmd = categoryToCmd;
		this.ingToCmd = ingToCmd;
		this.notesToCmd = notesToCmd;
	}
	
	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe recipe) {
		log.debug("Inside RecipeToRecipeCommand.convert function.");
		if(recipe == null) return null;
		
		final RecipeCommand cmd = new RecipeCommand();
		cmd.setId(recipe.getId());
		cmd.setCookTime(recipe.getCookTime());
		cmd.setPrepTime(recipe.getPrepTime());
		cmd.setDescription(recipe.getDescription());
		cmd.setDifficulty(recipe.getDifficulty());
		cmd.setDirections(recipe.getDirections());
		cmd.setServings(recipe.getServings());
		cmd.setSource(recipe.getSource());
		cmd.setUrl(recipe.getUrl());
		cmd.setImage(recipe.getImage());
		cmd.setNotes(notesToCmd.convert(recipe.getNotes()));
		
		if(recipe.getCategories() != null && recipe.getCategories().size() > 0)
			recipe.getCategories()
					.forEach((Category category) -> cmd.getCategories().add(categoryToCmd.convert(category)));
		
		if(recipe.getIngredients() != null && recipe.getIngredients().size() > 0)
			recipe.getIngredients()
					.forEach(ingredient -> cmd.getIngredients().add(ingToCmd.convert(ingredient)));
		
		return cmd;
	}
}
