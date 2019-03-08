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
        Kind kind = kindRepository.save(kindPredator("kind_name"));
        Keeper keeper = keeperRepository.save(keeper());
        Cage cage = cageRepository.save(new Cage());

        Animal expected = animal("name", kind, cage, keeper);
        Animal actual = animalRepository.save(expected);
        expected.setId(actual.getId());

        assertNotNull(actual.getId());
        assertEquals(expected, actual);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveWithDuplicateName() {
        Kind kind = kindRepository.save(kindPredator("kind_name"));
        Keeper keeper = keeperRepository.save(keeper());
        Cage cage = cageRepository.save(new Cage());

        animalRepository.save(animal("name", kind, cage, keeper));
        animalRepository.save(animal("name", kind, cage, keeper));
    }
}