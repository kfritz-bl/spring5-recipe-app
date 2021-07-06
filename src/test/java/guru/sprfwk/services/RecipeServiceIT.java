package guru.sprfwk.services;

import guru.sprfwk.commands.RecipeCommand;
import guru.sprfwk.converters.CommandToRecipe;
import guru.sprfwk.converters.RecipeToCommand;
import guru.sprfwk.domain.Recipe;
import guru.sprfwk.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;



@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {
	
	public static final String NEW_DESCRIPTION = "New Description";
	
	@Autowired
	RecipeService recipeSvc;
	
	@Autowired
	RecipeRepository recipeRepo;
	
	@Autowired
	CommandToRecipe cmdToRecipe;
	
	@Autowired
	RecipeToCommand recipeToCmd;
	
	@Transactional
	@Test
	public void testSaveOfDescription() {
		//given
		Iterable<Recipe> recipes = recipeRepo.findAll();
		Recipe recipe = recipes.iterator().next();
		RecipeCommand cmd = recipeToCmd.convert(recipe);
		
		//when
		assert cmd != null;
		cmd.setDescription(NEW_DESCRIPTION);
		RecipeCommand savedCmd = recipeSvc.saveRecipeCommand(cmd);
		
		//then
		assertEquals(NEW_DESCRIPTION, savedCmd.getDescription());
		assertEquals(recipe.getId(), savedCmd.getId());
		assertEquals(recipe.getCategories().size(), savedCmd.getCategories().size());
		assertEquals(recipe.getIngredients().size(), savedCmd.getIngredients().size());
	}
}
