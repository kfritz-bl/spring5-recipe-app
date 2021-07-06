package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class IngredientToCommandTest {
	
	public static final Recipe RECIPE = new Recipe();
	public static final BigDecimal AMOUNT = new BigDecimal("1");
	public static final String DESCRIPTION = "Cheeseburger";
	public static final Long UOM_ID = 2L;
	public static final Long ID_VALUE = 1L;
	
	
	IngredientToCommand converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new IngredientToCommand(new UnitOfMeasureToCommand());
	}
	
	@Test
	public void testNullConvert() {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() {
		assertNotNull(converter.convert(new Ingredient()));
	}
	
	@Test
	public void testConvertNullUOM() {
		//given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setRecipe(RECIPE);
		ingredient.setAmount(AMOUNT);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setUom(null);
		//when
		IngredientCommand cmd = converter.convert(ingredient);
		//then
		assert cmd != null;
		assertNull(cmd.getUom());
		assertEquals(ID_VALUE, cmd.getId());
		assertEquals(AMOUNT, cmd.getAmount());
		assertEquals(DESCRIPTION, cmd.getDescription());
	}
	
	@Test
	public void testConvertWithUom() {
		//given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setRecipe(RECIPE);
		ingredient.setAmount(AMOUNT);
		ingredient.setDescription(DESCRIPTION);
		
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(UOM_ID);
		
		ingredient.setUom(uom);
		//when
		IngredientCommand cmd = converter.convert(ingredient);
		//then
		assert cmd != null;
		assertEquals(ID_VALUE, cmd.getId());
		assertNotNull(cmd.getUom());
		assertEquals(UOM_ID, cmd.getUom().getId());
		// assertEquals(RECIPE, cmd.get);
		assertEquals(AMOUNT, cmd.getAmount());
		assertEquals(DESCRIPTION, cmd.getDescription());
	}
}
