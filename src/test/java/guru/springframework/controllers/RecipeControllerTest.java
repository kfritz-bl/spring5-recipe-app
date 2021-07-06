package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class RecipeControllerTest {
	
	@Mock
	RecipeService recipeSvc;
	
	RecipeController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		controller = new RecipeController(recipeSvc);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}
	
	@Test
	public void testGetRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		when(recipeSvc.findById(anyLong())).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/1/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testGetRecipeNotFound() throws Exception {
		when(recipeSvc.findById(anyLong())).thenThrow(NotFoundException.class);
		
		mockMvc.perform(get("/recipe/1/show"))
				.andExpect(status().isNotFound())
				.andExpect(view().name("error"))
				.andExpect(model().attributeExists("status"))
				.andExpect(model().attribute("status", 404));
	}
	
	@Test
	public void testGetRecipeNumberFormatException() throws Exception {
		mockMvc.perform(get("/recipe/asdf/show"))
				.andExpect(status().isBadRequest())
				.andExpect(view().name("error"))
				.andExpect(model().attributeExists("status"))
				.andExpect(model().attribute("status", 400));
	}
	
	@Test
	public void testGetNewRecipeForm() throws Exception {
		new RecipeCommand();
		
		mockMvc.perform(get("/recipe/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand cmd = new RecipeCommand();
		cmd.setId(2L);
		
		when(recipeSvc.saveRecipeCommand(any())).thenReturn(cmd);
		
		mockMvc.perform(
				post("/recipe")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", "")
						.param("description", "some string")
						.param("directions", "some directions"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/show"));
	}
	
	@Test
	public void testPostNewRecipeFormValidationFail() throws Exception {
		RecipeCommand cmd = new RecipeCommand();
		cmd.setId(2L);
		
		when(recipeSvc.saveRecipeCommand(any())).thenReturn(cmd);
		
		mockMvc.perform(
				post("/recipe")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", "")
						.param("cookTime", "3000"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("recipe"))
				.andExpect(view().name("recipe/recipeform"));
	}
	
	@Test
	public void testGetUpdateView() throws Exception {
		RecipeCommand cmd = new RecipeCommand();
		cmd.setId(2L);
		
		when(recipeSvc.findCommandById(anyLong())).thenReturn(cmd);
		
		mockMvc.perform(get("/recipe/1/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testDeleteAction() throws Exception {
		mockMvc.perform(get("/recipe/1/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
		
		verify(recipeSvc, times(1)).deleteById(anyLong());
	}
}
