package ru.ilya.zoo.repository;

import org.junit.Test;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.model.Cage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.ilya.zoo.utils.TestObjects.cage;

public class CageRepositoryTest extends IntegrationTest {

    @Test
    public void shouldSave() {
        Cage expected = cage(Integer.MAX_VALUE);
        Cage actual = cageRepository.save(expected);
        assertNotNull(actual.getId());
        assertEquals(expected.setId(actual.getId()), actual);
    }
}