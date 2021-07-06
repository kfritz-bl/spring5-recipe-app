package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class NotesToCommandTest {
	
	public static final Long ID_VALUE = 1L;
	public static final String RECIPE_NOTES = "Notes";
	NotesToCommand converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new NotesToCommand();
	}
	
	@Test
	public void convert() throws Exception {
		//given
		Notes notes = new Notes();
		notes.setId(ID_VALUE);
		notes.setRecipeNotes(RECIPE_NOTES);
		
		//when
		NotesCommand cmd = converter.convert(notes);
		
		//then
		assert cmd != null;
		assertEquals(ID_VALUE, cmd.getId());
		assertEquals(RECIPE_NOTES, cmd.getRecipeNotes());
	}
	
	@Test
	public void testNull() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() {
		assertNotNull(converter.convert(new Notes()));
	}
}
