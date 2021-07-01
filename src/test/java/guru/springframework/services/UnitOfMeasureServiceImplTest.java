package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToCommand uomToCmd = new UnitOfMeasureToCommand();

    UnitOfMeasureService uomSvc;

    @Mock
    UnitOfMeasureRepository uomRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        uomSvc = new UnitOfMeasureServiceImpl(uomRepo, uomToCmd);
    }

    @Test
    public void listAllUoms() {
        //given
        Set<UnitOfMeasure> uomSet = new HashSet<>();

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(1L);
        uomSet.add(uom);

        uom = new UnitOfMeasure();
        uom.setId(2L);
        uomSet.add(uom);

        when(uomRepo.findAll()).thenReturn(uomSet);

        //when
        Set<UnitOfMeasureCommand> cmdSet = uomSvc.listAllUoms();

        //then
        assertEquals(2, cmdSet.size());
        verify(uomRepo, times(1)).findAll();
    }
}
