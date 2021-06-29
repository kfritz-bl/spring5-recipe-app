package guru.springframework.services;


import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by jt on 6/17/17.
 */
public class RecipeServiceImplTest {
    //Create the RecipeService.
    RecipeServiceImpl recipeService;

    //Create a mock RecipeRepository.
    @Mock
    RecipeRepository recipeRepository;


    @Before
    public void setUp() throws Exception {
        //Initialize the Mockito mocks.
        MockitoAnnotations.initMocks(this);

        //Initialize the RecipeService.
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipes() throws Exception {
        //given - Setup the necessary things. This is the initial state.
        //Create a HashSet with a Recipe.
        Recipe recipe = new Recipe();
        HashSet receipesData = new HashSet();
        receipesData.add(recipe);

        //Tell Mockito to return our HashSet when recipeService.getRecipes() is called.
        when(recipeService.getRecipes()).thenReturn(receipesData);

        //when - Setup what we are testing.
        //Use the RecipeService to get the recipes.
        Set<Recipe> recipes = recipeService.getRecipes();

        //then - Run the test and check the results. This is what we're expecting from the behavior.
        //Check that the size of recipes is one.
        assertEquals(recipes.size(), 1);

        //Verify the RecipeRepository was called once and only once.
        verify(recipeRepository, times(1)).findAll();
    }
}
