package guru.springframework.services;


import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.CommandToRecipe;
import guru.springframework.converters.RecipeToCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


public class RecipeServiceImplTest {
	
	RecipeServiceImpl recipeSvc;
	
	@Mock
	RecipeRepository recipeRepo;
	
	@Mock
	RecipeToCommand recipeToCmd;
	
	@Mock
	CommandToRecipe cmdToRecipe;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		recipeSvc = new RecipeServiceImpl(recipeRepo, cmdToRecipe, recipeToCmd);
	}
	
	@Test
	public void getRecipeByIdTest() {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOpt = Optional.of(recipe);
		
		when(recipeRepo.findById(anyLong())).thenReturn(recipeOpt);
		
		Recipe savedRecipe = recipeSvc.findById(1L);
		
		assertNotNull("Null recipe returned", savedRecipe);
		verify(recipeRepo, times(1)).findById(anyLong());
		verify(recipeRepo, never()).findAll();
	}
	
	@Test(expected = NotFoundException.class)
	public void getRecipeByIdTestNotFound() {
		Optional<Recipe> recipeOpt = Optional.empty();
		when(recipeRepo.findById(anyLong())).thenReturn(recipeOpt);
		recipeSvc.findById(1L);
		//should throw NotFoundException
	}
	
	@Test
	public void getRecipeCommandByIdTest() {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOpt = Optional.of(recipe);
		
		when(recipeRepo.findById(anyLong())).thenReturn(recipeOpt);
		
		RecipeCommand cmd = new RecipeCommand();
		cmd.setId(1L);
		
		when(recipeToCmd.convert(any())).thenReturn(cmd);
		
		RecipeCommand savedCmd = recipeSvc.findCommandById(1L);
		
		assertNotNull("Null recipe returned", savedCmd);
		verify(recipeRepo, times(1)).findById(anyLong());
		verify(recipeRepo, never()).findAll();
	}
	
	@Test
	public void getRecipesTest() {
		Recipe recipe = new Recipe();
		HashSet<Recipe> recipesData = new HashSet<>();
		recipesData.add(recipe);
		
		when(recipeSvc.getRecipes()).thenReturn(recipesData);
		
		Set<Recipe> recipes = recipeSvc.getRecipes();
		
		assertEquals(recipes.size(), 1);
		verify(recipeRepo, times(1)).findAll();
		verify(recipeRepo, never()).findById(anyLong());
	}
	
	@Test
	public void testDeleteById() {
		//given
		Long id = 2L;
		
		//when
		recipeSvc.deleteById(id);
		
		//no 'when', since method has void return type
		
		//then
		verify(recipeRepo, times(1)).deleteById(anyLong());
	}
}
