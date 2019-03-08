package ru.ilya.zoo.repository;

import org.junit.Test;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.IntegrationTest;

import static org.junit.Assert.*;

public class CageRepositoryTest extends IntegrationTest {

    @Test
    public void shouldSave() {
        Cage expected = new Cage().setCapacity(Integer.MAX_VALUE);
        Cage actual = cageRepository.save(expected);
        assertNotNull(actual.getId());
        assertEquals(expected.setId(actual.getId()), actual);
    }
}