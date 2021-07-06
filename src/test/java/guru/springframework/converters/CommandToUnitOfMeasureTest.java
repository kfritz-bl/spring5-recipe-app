package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandToUnitOfMeasureTest {
	
	public static final String DESCRIPTION = "description";
	public static final Long LONG_VALUE = 1L;
	
	CommandToUnitOfMeasure converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new CommandToUnitOfMeasure();
	}
	
	@Test
	public void testNullParameter() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() {
		assertNotNull(converter.convert(new UnitOfMeasureCommand()));
	}
	
	@Test
	public void convert() throws Exception {
		//given
		UnitOfMeasureCommand cmd = new UnitOfMeasureCommand();
		cmd.setId(LONG_VALUE);
		cmd.setDescription(DESCRIPTION);
		
		//when
		UnitOfMeasure uom = converter.convert(cmd);
		
		//then
		assertNotNull(uom);
		assertEquals(LONG_VALUE, uom.getId());
		assertEquals(DESCRIPTION, uom.getDescription());
	}
}
