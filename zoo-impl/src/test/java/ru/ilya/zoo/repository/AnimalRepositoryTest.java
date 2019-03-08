package ru.ilya.zoo.repository;

import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.ilya.zoo.utils.TestUtils.*;

public class AnimalRepositoryTest extends IntegrationTest {

    @Test
    public void shouldSave() {
        Kind kind = save(kindPredator("kind_name"));
        Keeper keeper = save(keeper());
        Cage cage = save(new Cage());

        Animal expected = animal("name", kind, cage, keeper);
        Animal actual = save(expected);
        expected.setId(actual.getId());

        assertNotNull(actual.getId());
        assertEquals(expected, actual);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveWithDuplicateName() {
        Kind kind = save(kindPredator("kind_name"));
        Keeper keeper = save(keeper());
        Cage cage = save(new Cage());

        save(animal("name", kind, cage, keeper),
             animal("name", kind, cage, keeper));
    }
}