package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeToCommandTest {
	
	public static final Long RECIPE_ID = 1L;
	public static final Integer COOK_TIME = Integer.valueOf("5");
	public static final Integer PREP_TIME = Integer.valueOf("7");
	public static final String DESCRIPTION = "My Recipe";
	public static final String DIRECTIONS = "Directions";
	public static final Difficulty DIFFICULTY = Difficulty.EASY;
	public static final Integer SERVINGS = Integer.valueOf("3");
	public static final String SOURCE = "Source";
	public static final String URL = "Some URL";
	public static final Long CAT_ID_1 = 1L;
	public static final Long CAT_ID_2 = 2L;
	public static final Long ING_ID_1 = 3L;
	public static final Long ING_ID_2 = 4L;
	public static final Long NOTES_ID = 9L;
	RecipeToCommand converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new RecipeToCommand(
				new CategoryToCommand(),
				new IngredientToCommand(
						new UnitOfMeasureToCommand()),
				new NotesToCommand());
	}
	
	@Test
	public void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() {
		assertNotNull(converter.convert(new Recipe()));
	}
	
	@Test
	public void convert() throws Exception {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);
		recipe.setCookTime(COOK_TIME);
		recipe.setPrepTime(PREP_TIME);
		recipe.setDescription(DESCRIPTION);
		recipe.setDifficulty(DIFFICULTY);
		recipe.setDirections(DIRECTIONS);
		recipe.setServings(SERVINGS);
		recipe.setSource(SOURCE);
		recipe.setUrl(URL);
		
		Notes notes = new Notes();
		notes.setId(NOTES_ID);
		recipe.setNotes(notes);
		
		Category category = new Category();
		category.setId(CAT_ID_1);
		recipe.getCategories().add(category);
		
		category = new Category();
		category.setId(CAT_ID_2);
		recipe.getCategories().add(category);
		
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ING_ID_1);
		recipe.getIngredients().add(ingredient);
		
		ingredient = new Ingredient();
		ingredient.setId(ING_ID_2);
		recipe.getIngredients().add(ingredient);
		
		//when
		RecipeCommand cmd = converter.convert(recipe);
		
		//then
		assertNotNull(cmd);
		assertEquals(RECIPE_ID, cmd.getId());
		assertEquals(COOK_TIME, cmd.getCookTime());
		assertEquals(PREP_TIME, cmd.getPrepTime());
		assertEquals(DESCRIPTION, cmd.getDescription());
		assertEquals(DIFFICULTY, cmd.getDifficulty());
		assertEquals(DIRECTIONS, cmd.getDirections());
		assertEquals(SERVINGS, cmd.getServings());
		assertEquals(SOURCE, cmd.getSource());
		assertEquals(URL, cmd.getUrl());
		assertEquals(NOTES_ID, cmd.getNotes().getId());
		assertEquals(2, cmd.getCategories().size());
		assertEquals(2, cmd.getIngredients().size());
	}
}
