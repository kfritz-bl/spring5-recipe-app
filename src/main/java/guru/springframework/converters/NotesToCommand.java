package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class NotesToCommand implements Converter<Notes, NotesCommand> {
	
	@Synchronized
	@Nullable
	@Override
	public NotesCommand convert(Notes notes) {
		log.debug("Inside NotesToNotesCommand.convert function.");
		if(notes == null) return null;
		
		final NotesCommand cmd = new NotesCommand();
		cmd.setId(notes.getId());
		cmd.setRecipeNotes(notes.getRecipeNotes());
		return cmd;
	}
}
