package guru.springframework.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by jt on 6/17/17.
 */
public class CategoryTest {

    Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() {
        Long idValue = 4L;

        category.setId(idValue);

        assertEquals(idValue, category.getId());
    }

    @Test
    public void getDescription() {
        String desc = "some description";
        category.setDescription(desc);
        assertEquals(desc, category.getDescription());
    }

    @Test
    public void getRecipes() {
        //Recipe r = new Recipe();
        //category.getRecipes().add(r);
        //assertEquals(r, category.getRecipes().stream().findFirst());
    }
}
