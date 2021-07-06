package guru.sprfwk.services;

import guru.sprfwk.commands.UnitOfMeasureCommand;

import java.util.Set;


public interface UnitOfMeasureService {
	
	Set<UnitOfMeasureCommand> listAllUoms();
}
