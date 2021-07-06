package guru.sprfwk.converters;

import guru.sprfwk.commands.UnitOfMeasureCommand;
import guru.sprfwk.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class UnitOfMeasureToCommandTest {
	
	public static final String DESCRIPTION = "description";
	public static final Long LONG_VALUE = 1L;
	
	UnitOfMeasureToCommand converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new UnitOfMeasureToCommand();
	}
	
	@Test
	public void testNullObjectConvert() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObj() {
		assertNotNull(converter.convert(new UnitOfMeasure()));
	}
	
	@Test
	public void convert() throws Exception {
		//given
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(LONG_VALUE);
		uom.setDescription(DESCRIPTION);
		
		//when
		UnitOfMeasureCommand cmd = converter.convert(uom);
		
		//then
		assert cmd != null;
		assertEquals(LONG_VALUE, cmd.getId());
		assertEquals(DESCRIPTION, cmd.getDescription());
	}
}
