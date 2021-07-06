package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CommandToIngredient implements Converter<IngredientCommand, Ingredient> {
	
	private final CommandToUnitOfMeasure uomConverter;
	
	public CommandToIngredient(CommandToUnitOfMeasure uomConverter) {
		this.uomConverter = uomConverter;
	}
	
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand cmd) {
		log.debug("Inside IngredientCommandToIngredient.convert function.");
		if(cmd == null) return null;
		
		final Ingredient ingredient = new Ingredient();
		ingredient.setId(cmd.getId());
		
		if(cmd.getRecipeId() != null) {
			Recipe recipe = new Recipe();
			recipe.setId(cmd.getRecipeId());
			ingredient.setRecipe(recipe);
			recipe.addIngredient(ingredient);
		}
		
		ingredient.setAmount(cmd.getAmount());
		ingredient.setDescription(cmd.getDescription());
		ingredient.setUom(uomConverter.convert(cmd.getUom()));
		return ingredient;
	}
}
