package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CommandToIngredientTest {
	
	//public static final Recipe RECIPE = new Recipe();
	public static final BigDecimal AMOUNT = new BigDecimal("1");
	public static final String DESCRIPTION = "Cheeseburger";
	public static final Long ID_VALUE = 1L;
	public static final Long UOM_ID = 2L;
	
	CommandToIngredient converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new CommandToIngredient(new CommandToUnitOfMeasure());
	}
	
	@Test
	public void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() {
		assertNotNull(converter.convert(new IngredientCommand()));
	}
	
	@Test
	public void convert() throws Exception {
		//given
		IngredientCommand cmd = new IngredientCommand();
		cmd.setId(ID_VALUE);
		cmd.setAmount(AMOUNT);
		cmd.setDescription(DESCRIPTION);
		UnitOfMeasureCommand uomCmd = new UnitOfMeasureCommand();
		uomCmd.setId(UOM_ID);
		cmd.setUom(uomCmd);
		
		//when
		Ingredient ingredient = converter.convert(cmd);
		
		//then
		assertNotNull(ingredient);
		assertNotNull(ingredient.getUom());
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(AMOUNT, ingredient.getAmount());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		assertEquals(UOM_ID, ingredient.getUom().getId());
	}
	
	@Test
	public void convertWithNullUOM() {
		//given
		IngredientCommand cmd = new IngredientCommand();
		cmd.setId(ID_VALUE);
		cmd.setAmount(AMOUNT);
		cmd.setDescription(DESCRIPTION);
		
		//when
		Ingredient ingredient = converter.convert(cmd);
		
		//then
		assertNotNull(ingredient);
		assertNull(ingredient.getUom());
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(AMOUNT, ingredient.getAmount());
		assertEquals(DESCRIPTION, ingredient.getDescription());
	}
}
