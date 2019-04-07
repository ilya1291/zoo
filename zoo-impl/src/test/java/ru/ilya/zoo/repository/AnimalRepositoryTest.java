package ru.ilya.zoo.repository;

import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.ilya.zoo.utils.TestObjects.*;

public class AnimalRepositoryTest extends IntegrationTest {

    @Test
    public void shouldSave() {
        Kind kind = save(kindPredator("kind_name"));
        Keeper keeper = save(keeper());
        Cage cage = save(new Cage());

        Animal expected = animal("name", kind, cage.getId(), keeper);
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

        save(animal("name", kind, cage.getId(), keeper),
             animal("name", kind, cage.getId(), keeper));
    }

    @Test
    @Rollback
    @Transactional
    public void shouldFindAllAsStream() {
        Kind kind = save(kindPredator("kind_name"));
        Keeper keeper = save(keeper());
        Cage cage = save(new Cage());

        Animal expected = save(animal("name", kind, cage.getId(), keeper));

        try(Stream<Animal> animals = animalRepository.findAllAsStream()) {
            animals.forEach(a -> assertEquals(expected, a));
        }
    }
}