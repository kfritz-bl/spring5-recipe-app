package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jt on 6/21/17.
 */
public class CategoryToCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "descript";
    CategoryToCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void convert() throws Exception {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand cmd = converter.convert(category);

        //then
        assert cmd != null;
        assertEquals(ID_VALUE, cmd.getId());
        assertEquals(DESCRIPTION, cmd.getDescription());
    }
}
