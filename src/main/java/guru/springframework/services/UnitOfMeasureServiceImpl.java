package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToCommand;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by jt on 6/28/17.
 */
@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository uomRepo;
    private final UnitOfMeasureToCommand uomToCmd;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepo,
                                    UnitOfMeasureToCommand uomToCmd) {
        this.uomRepo = uomRepo;
        this.uomToCmd = uomToCmd;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        log.debug("Getting the list of all UOMs");
        return StreamSupport.stream(uomRepo.findAll()
                .spliterator(), false)
                .map(uomToCmd::convert)
                .collect(Collectors.toSet());
    }
}
