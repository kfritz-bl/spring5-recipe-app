package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by jt on 6/17/17.
 */
public class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController controller;

    @Before
    public void setUp() {
        //Initialize the Mockito mocks.
        MockitoAnnotations.initMocks(this);

        //Initialize the IndexController.
        controller = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {
        //Main entry point for server-side Spring MVC test support.
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        //Perform a request and check for status 200 and 'index' is returned.
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {

        //given - Setup the necessary things. This is the initial state.
        //Create a HashSet with two Recipes.
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        recipes.add(recipe);

        //Tell Mockito to return our HashSet when recipeService.getRecipes() is called.
        when(recipeService.getRecipes()).thenReturn(recipes);

        //Create an ArgumentCaptor to capture the HashSet and store it for later verification.
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);


        //when - Setup what we are testing.
        String viewName = controller.getIndexPage(model);


        //then - Run the test and check the results. This is what we're expecting from the behavior.
        //Check that the controller name matches 'index'.
        assertEquals("index", viewName);

        //Verify that RecipeService returned one and only one recipe.
        verify(recipeService, times(1)).getRecipes();

        //Verify that our Model returns one and only one set of recipes and capture that set.
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        //Also check that our ArgumentCaptor Set contains two elements.
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}
