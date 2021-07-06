package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.CommandToIngredient;
import guru.springframework.converters.IngredientToCommand;
import guru.springframework.converters.CommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {
	
	private final IngredientToCommand ingToCmd;
	private final CommandToIngredient cmdToIng;
	
	@Mock
	RecipeRepository recipeRepo;
	
	@Mock
	UnitOfMeasureRepository uomRepo;
	
	IngredientService ingSvc;
	
	//init converters
	public IngredientServiceImplTest() {
		this.ingToCmd = new IngredientToCommand(new UnitOfMeasureToCommand());
		this.cmdToIng = new CommandToIngredient(new CommandToUnitOfMeasure());
	}
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		ingSvc = new IngredientServiceImpl(ingToCmd, cmdToIng, recipeRepo, uomRepo);
	}
	
	@Test
	public void findByRecipeIdAndId() {
	}
	
	@Test
	public void findByRecipeIdAndRecipeIdHappyPath() {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ingredient = new Ingredient();
		ingredient.setId(1L);
		recipe.addIngredient(ingredient);
		
		ingredient = new Ingredient();
		ingredient.setId(1L);
		recipe.addIngredient(ingredient);
		
		ingredient = new Ingredient();
		ingredient.setId(3L);
		recipe.addIngredient(ingredient);
		
		Optional<Recipe> recipeOpt = Optional.of(recipe);
		
		when(recipeRepo.findById(anyLong())).thenReturn(recipeOpt);
		
		//then
		IngredientCommand cmd = ingSvc.findByRecipeIdAndIngredientId(1L, 3L);
		
		//when
		assertEquals(Long.valueOf(3L), cmd.getId());
		assertEquals(Long.valueOf(1L), cmd.getRecipeId());
		verify(recipeRepo, times(1)).findById(anyLong());
	}
	
	@Test
	public void testSaveRecipeCommand() {
		//given
		IngredientCommand cmd = new IngredientCommand();
		cmd.setId(3L);
		cmd.setRecipeId(2L);
		
		Optional<Recipe> recipeOpt = Optional.of(new Recipe());
		
		Recipe recipe = new Recipe();
		recipe.addIngredient(new Ingredient());
		recipe.getIngredients().iterator().next().setId(3L);
		
		when(recipeRepo.findById(anyLong())).thenReturn(recipeOpt);
		when(recipeRepo.save(any())).thenReturn(recipe);
		
		//when
		IngredientCommand savedCmd = ingSvc.saveIngredientCommand(cmd);
		
		//then
		assertEquals(Long.valueOf(3L), savedCmd.getId());
		verify(recipeRepo, times(1)).findById(anyLong());
		verify(recipeRepo, times(1)).save(any(Recipe.class));
	}
	
	@Test
	public void testDeleteById() {
		//given
		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		ingredient.setId(3L);
		recipe.addIngredient(ingredient);
		ingredient.setRecipe(recipe);
		Optional<Recipe> recipeOpt = Optional.of(recipe);
		
		when(recipeRepo.findById(anyLong())).thenReturn(recipeOpt);
		
		//when
		ingSvc.deleteById(1L, 3L);
		
		//then
		verify(recipeRepo, times(1)).findById(anyLong());
		verify(recipeRepo, times(1)).save(any(Recipe.class));
	}
}
