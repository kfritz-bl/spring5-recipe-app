package guru.sprfwk.converters;

import guru.sprfwk.commands.CategoryCommand;
import guru.sprfwk.domain.Category;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CategoryToCommand implements Converter<Category, CategoryCommand> {
	
	@Synchronized
	@Nullable
	@Override
	public CategoryCommand convert(Category category) {
		log.debug("Inside CategoryToCategoryCommand.convert function.");
		if(category == null) return null;
		
		final CategoryCommand cmd = new CategoryCommand();
		cmd.setId(category.getId());
		cmd.setDescription(category.getDescription());
		return cmd;
	}
}
