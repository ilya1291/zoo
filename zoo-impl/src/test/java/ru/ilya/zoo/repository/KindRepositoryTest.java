package ru.ilya.zoo.repository;

import org.junit.Test;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;

import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestUtils.*;

public class KindRepositoryTest extends IntegrationTest {

    @Test
    public void shouldSave() {
        Kind expected = kindPredator("wolf");
        Kind actual = kindRepository.save(expected);
        expected.setId(actual.getId());

        assertNotNull(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void shouldDeleteAnimalsCascade() {
        Kind kind = kindRepository.save(kindPredator("kind_name"));
        Keeper keeper = keeperRepository.save(keeper());
        Cage cage = cageRepository.save(new Cage());
        Animal animal = animalRepository.save(animal("name", kind, cage, keeper));

        kindRepository.delete(kind);

        assertFalse(kindRepository.existsById(kind.getId()));
        assertFalse(animalRepository.existsById(animal.getId()));
    }
}