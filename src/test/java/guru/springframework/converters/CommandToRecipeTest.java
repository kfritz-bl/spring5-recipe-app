package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandToRecipeTest {
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

    CommandToRecipe converter;


    @Before
    public void setUp() throws Exception {
        converter = new CommandToRecipe(
                new CommandToCategory(),
                new CommandToIngredient(
                        new CommandToUnitOfMeasure()),
                new CommandToNotes());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        RecipeCommand cmd = new RecipeCommand();
        cmd.setId(RECIPE_ID);
        cmd.setCookTime(COOK_TIME);
        cmd.setPrepTime(PREP_TIME);
        cmd.setDescription(DESCRIPTION);
        cmd.setDifficulty(DIFFICULTY);
        cmd.setDirections(DIRECTIONS);
        cmd.setServings(SERVINGS);
        cmd.setSource(SOURCE);
        cmd.setUrl(URL);

        NotesCommand notesCmd = new NotesCommand();
        notesCmd.setId(NOTES_ID);
        cmd.setNotes(notesCmd);

        CategoryCommand categoryCmd = new CategoryCommand();
        categoryCmd.setId(CAT_ID_1);
        cmd.getCategories().add(categoryCmd);

        categoryCmd = new CategoryCommand();
        categoryCmd.setId(CAT_ID_2);
        cmd.getCategories().add(categoryCmd);

        IngredientCommand ingCmd = new IngredientCommand();
        ingCmd.setId(ING_ID_1);
        cmd.getIngredients().add(ingCmd);

        ingCmd = new IngredientCommand();
        ingCmd.setId(ING_ID_2);
        cmd.getIngredients().add(ingCmd);

        //when
        Recipe recipe = converter.convert(cmd);

        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}
