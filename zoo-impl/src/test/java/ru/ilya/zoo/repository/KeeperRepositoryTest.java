package ru.ilya.zoo.repository;

import org.junit.Test;
import ru.ilya.zoo.IntegrationTest;
import ru.ilya.zoo.model.Keeper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.ilya.zoo.utils.TestUtils.keeper;

public class KeeperRepositoryTest extends IntegrationTest {

    @Test
    public void shouldSave() {
        Keeper expected = keeper();

        Keeper actual = keeperRepository.save(expected);

        assertNotNull(actual.getId());
        assertEquals(expected, actual.setId(expected.getId()));
    }

}