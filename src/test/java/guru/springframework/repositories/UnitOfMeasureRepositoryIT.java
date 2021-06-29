package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by jt on 6/17/17.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() {
    }

    @Test
    public void findByDescription() {
        //Get a UnitOfMeasure by description 'Teaspoon'.
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        //Check that the repository returned the correct result.
        assertEquals("Teaspoon", uomOptional.get().getDescription());
    }

    @Test
    public void findByDescriptionCup() {
        //Get a UnitOfMeasure by description 'Cup'.
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Cup");

        //Check that the repository returned the correct result.
        assertEquals("Cup", uomOptional.get().getDescription());
    }
}
