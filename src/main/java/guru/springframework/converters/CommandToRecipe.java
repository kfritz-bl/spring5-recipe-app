package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CommandToRecipe implements Converter<RecipeCommand, Recipe> {
	
	private final CommandToCategory cmdToCategory;
	private final CommandToIngredient cmdToIng;
	private final CommandToNotes cmdToNotes;
	
	public CommandToRecipe(CommandToCategory cmdToCategory,
						   CommandToIngredient cmdToIng,
						   CommandToNotes cmdToNotes) {
		this.cmdToCategory = cmdToCategory;
		this.cmdToIng = cmdToIng;
		this.cmdToNotes = cmdToNotes;
	}
	
	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand cmd) {
		log.debug("Inside RecipeCommandToRecipe.convert function.");
		if(cmd == null) return null;
		
		final Recipe recipe = new Recipe();
		recipe.setId(cmd.getId());
		recipe.setCookTime(cmd.getCookTime());
		recipe.setPrepTime(cmd.getPrepTime());
		recipe.setDescription(cmd.getDescription());
		recipe.setDifficulty(cmd.getDifficulty());
		recipe.setDirections(cmd.getDirections());
		recipe.setServings(cmd.getServings());
		recipe.setSource(cmd.getSource());
		recipe.setUrl(cmd.getUrl());
		recipe.setImage(cmd.getImage());
		recipe.setNotes(cmdToNotes.convert(cmd.getNotes()));
		
		if(cmd.getCategories() != null && cmd.getCategories().size() > 0)
			cmd.getCategories()
					.forEach(category -> recipe.getCategories().add(cmdToCategory.convert(category)));
		
		if(cmd.getIngredients() != null && cmd.getIngredients().size() > 0)
			cmd.getIngredients()
					.forEach(ingredient -> recipe.getIngredients().add(cmdToIng.convert(ingredient)));
		
		return recipe;
	}
}
