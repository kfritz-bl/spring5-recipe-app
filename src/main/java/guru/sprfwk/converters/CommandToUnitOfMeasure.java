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
public class CommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {
	
	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasure convert(UnitOfMeasureCommand cmd) {
		log.debug("Inside UnitOfMeasureCommandToUnitOfMeasure.convert function.");
		if(cmd == null) return null;
		
		final UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(cmd.getId());
		uom.setDescription(cmd.getDescription());
		return uom;
	}
}
