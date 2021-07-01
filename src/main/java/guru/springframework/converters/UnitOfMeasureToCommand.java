package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Slf4j
@Component
public class UnitOfMeasureToCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure uom) {
        log.debug("Inside UnitOfMeasureToUnitOfMeasureCommand.convert function.");
        if (uom == null) return null;

        final UnitOfMeasureCommand cmd = new UnitOfMeasureCommand();
        cmd.setId(uom.getId());
        cmd.setDescription(uom.getDescription());
        return cmd;
    }
}
