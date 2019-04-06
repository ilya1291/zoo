package ru.ilya.zoo.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Kind;
import ru.ilya.zoo.repository.KindRepository;
import ru.ilya.zoo.utils.TestObjects;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class KindServiceTest {

    @Mock
    private KindRepository kindRepository;

    @InjectMocks
    private KindService kindService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        reset(kindRepository);
    }

    @Test
    public void shouldGetByName() {
        Kind expected = TestObjects.kind();
        when(kindRepository.findByNameIgnoringCase(expected.getName())).thenReturn(Optional.of(expected));
        Kind actual = kindService.getByName(expected.getName());
        assertEquals(expected, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrow_WhenRoleNotFound() {
        kindService.getByName("");
    }
}