package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
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
public class CommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand cmd) {
        log.debug("Inside NotesCommandToNotes.convert function.");
        if (cmd == null) return null;

        final Notes notes = new Notes();
        notes.setId(cmd.getId());
        notes.setRecipeNotes(cmd.getRecipeNotes());
        return notes;
    }
}
