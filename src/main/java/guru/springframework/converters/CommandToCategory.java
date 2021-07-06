package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CommandToCategory implements Converter<CategoryCommand, Category> {
	
	@Synchronized
	@Nullable
	@Override
	public Category convert(CategoryCommand cmd) {
		log.debug("Inside CategoryCommandToCategory.convert function.");
		if(cmd == null) return null;
		
		final Category category = new Category();
		category.setId(cmd.getId());
		category.setDescription(cmd.getDescription());
		return category;
	}
}
