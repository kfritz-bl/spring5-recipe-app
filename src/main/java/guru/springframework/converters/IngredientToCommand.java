package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class IngredientToCommand implements Converter<Ingredient, IngredientCommand> {
	
	private final UnitOfMeasureToCommand uomConverter;
	
	public IngredientToCommand(UnitOfMeasureToCommand uomConverter) {
		this.uomConverter = uomConverter;
	}
	
	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient ingredient) {
		log.debug("Inside IngredientToIngredientCommand.convert function.");
		if(ingredient == null) return null;
		
		IngredientCommand cmd = new IngredientCommand();
		cmd.setId(ingredient.getId());
		if(ingredient.getRecipe() != null) cmd.setRecipeId(ingredient.getRecipe().getId());
		cmd.setAmount(ingredient.getAmount());
		cmd.setDescription(ingredient.getDescription());
		cmd.setUom(uomConverter.convert(ingredient.getUom()));
		return cmd;
	}
}
