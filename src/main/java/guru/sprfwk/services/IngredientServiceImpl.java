package guru.sprfwk.services;

import guru.sprfwk.commands.IngredientCommand;
import guru.sprfwk.converters.CommandToIngredient;
import guru.sprfwk.converters.IngredientToCommand;
import guru.sprfwk.domain.Ingredient;
import guru.sprfwk.domain.Recipe;
import guru.sprfwk.repositories.RecipeRepository;
import guru.sprfwk.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
	
	private final IngredientToCommand ingToCmd;
	private final CommandToIngredient cmdToIng;
	private final RecipeRepository recipeRepo;
	private final UnitOfMeasureRepository uomRepo;
	
	public IngredientServiceImpl(IngredientToCommand ingToCmd,
								 CommandToIngredient cmdToIng,
								 RecipeRepository recipeRepo,
								 UnitOfMeasureRepository uomRepo) {
		this.ingToCmd = ingToCmd;
		this.cmdToIng = cmdToIng;
		this.recipeRepo = recipeRepo;
		this.uomRepo = uomRepo;
	}
	
	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
		log.debug("Searching for ingredient id " + id + " for recipe id " + recipeId);
		Optional<Recipe> recipeOpt = recipeRepo.findById(recipeId);
		
		if(!recipeOpt.isPresent()) {
			//todo impl error handling
			log.error("recipe id not found. Id: " + recipeId);
			return null;
		}
		
		Recipe recipe = recipeOpt.get();
		
		Optional<IngredientCommand> ingredientCmdOpt = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(id))
				.map(ingToCmd::convert).findFirst();
		
		if(!ingredientCmdOpt.isPresent()) {
			//todo impl error handling
			log.error("Ingredient id not found: " + id);
		}
		
		return (ingredientCmdOpt.orElse(null));
	}
	
	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand cmd) {
		log.debug("Saving ingredient id " + cmd.getId() + " for recipe id " + cmd.getRecipeId());
		Optional<Recipe> recipeOpt = recipeRepo.findById(cmd.getRecipeId());
		
		if(!recipeOpt.isPresent()) {
			//todo toss error if not found!
			log.error("Recipe not found for id: " + cmd.getRecipeId());
			return new IngredientCommand();
		}
		else {
			Recipe recipe = recipeOpt.get();
			
			Optional<Ingredient> ingredientOpt = recipe
					.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(cmd.getId()))
					.findFirst();
			
			if(ingredientOpt.isPresent()) {
				Ingredient ingredientFound = ingredientOpt.get();
				ingredientFound.setDescription(cmd.getDescription());
				ingredientFound.setAmount(cmd.getAmount());
				ingredientFound.setUom(uomRepo
						.findById(cmd.getUom().getId())
						.orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
			}
			else {
				//add new Ingredient
				Ingredient ingredient = cmdToIng.convert(cmd);
				assert ingredient != null;
				ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
			}
			
			Recipe savedRecipe = recipeRepo.save(recipe);
			
			Optional<Ingredient> savedIngredientOpt = savedRecipe.getIngredients().stream()
					.filter(recipeIngredients -> recipeIngredients.getId().equals(cmd.getId()))
					.findFirst();
			
			//check by description
			if(!savedIngredientOpt.isPresent()) {
				//not totally safe... But best guess
				savedIngredientOpt = savedRecipe.getIngredients().stream()
						.filter(recipeIngredients -> recipeIngredients.getDescription().equals(cmd.getDescription()))
						.filter(recipeIngredients -> recipeIngredients.getAmount().equals(cmd.getAmount()))
						.filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(cmd.getUom().getId()))
						.findFirst();
			}
			
			//to do check for fail
			return (savedIngredientOpt.map(ingToCmd::convert).orElse(null));
		}
	}
	
	@Override
	public void deleteById(Long recipeId, Long id) {
		log.debug("Deleting ingredient: " + recipeId + ":" + id);
		Optional<Recipe> recipeOpt = recipeRepo.findById(recipeId);
		
		if(recipeOpt.isPresent()) {
			Recipe recipe = recipeOpt.get();
			log.debug("found recipe");
			
			Optional<Ingredient> ingredientOpt = recipe
					.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(id))
					.findFirst();
			
			if(ingredientOpt.isPresent()) {
				log.debug("found Ingredient");
				Ingredient ingredientToDelete = ingredientOpt.get();
				ingredientToDelete.setRecipe(null);
				recipe.getIngredients().remove(ingredientOpt.get());
				recipeRepo.save(recipe);
			}
		}
		else {
			log.debug("Recipe Id Not found. Id:" + recipeId);
		}
	}
}
