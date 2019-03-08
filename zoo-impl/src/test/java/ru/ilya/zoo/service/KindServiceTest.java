package ru.ilya.zoo.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Kind;
import ru.ilya.zoo.repository.KindRepository;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static ru.ilya.zoo.utils.TestUtils.kind;

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
    public void shouldDelete() {
        Kind kind = kind().setId(1L);
        Mockito.when(kindRepository.existsById(kind.getId())).thenReturn(true);

        kindService.delete(kind.getId());
        verify(kindRepository).existsById(kind.getId());
        verify(kindRepository).deleteById(kind.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIfNotExists_whenDeleting() {
        kindService.delete(Long.MAX_VALUE);
    }
}