package guru.sprfwk.converters;

import guru.sprfwk.commands.UnitOfMeasureCommand;
import guru.sprfwk.domain.UnitOfMeasure;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class UnitOfMeasureToCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
	
	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure uom) {
		log.debug("Inside UnitOfMeasureToUnitOfMeasureCommand.convert function.");
		if(uom == null) return null;
		
		final UnitOfMeasureCommand cmd = new UnitOfMeasureCommand();
		cmd.setId(uom.getId());
		cmd.setDescription(uom.getDescription());
		return cmd;
	}
}
