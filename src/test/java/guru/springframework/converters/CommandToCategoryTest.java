package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandToCategoryTest {
	
	public static final Long ID_VALUE = 1L;
	public static final String DESCRIPTION = "description";
	CommandToCategory converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new CommandToCategory();
	}
	
	@Test
	public void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() {
		assertNotNull(converter.convert(new CategoryCommand()));
	}
	
	@Test
	public void convert() throws Exception {
		//given
		CategoryCommand cmd = new CategoryCommand();
		cmd.setId(ID_VALUE);
		cmd.setDescription(DESCRIPTION);
		
		//when
		Category category = converter.convert(cmd);
		
		//then
		assert category != null;
		assertEquals(ID_VALUE, category.getId());
		assertEquals(DESCRIPTION, category.getDescription());
	}
}
