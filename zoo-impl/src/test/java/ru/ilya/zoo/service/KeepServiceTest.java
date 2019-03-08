package ru.ilya.zoo.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.repository.KeeperRepository;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static ru.ilya.zoo.utils.TestUtils.keeper;

public class KeepServiceTest {

    @Mock
    private KeeperRepository keeperRepository;

    @InjectMocks
    private KeepService keepService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        reset(keeperRepository);
    }

    @Test
    public void shouldDelete() {
        Keeper keeper = keeper().setId(1L);
        Mockito.when(keeperRepository.existsById(keeper.getId())).thenReturn(true);

        keepService.delete(keeper.getId());
        verify(keeperRepository).existsById(keeper.getId());
        verify(keeperRepository).deleteById(keeper.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIfNotExists_whenDeleting() {
        keepService.delete(Long.MAX_VALUE);
    }
}