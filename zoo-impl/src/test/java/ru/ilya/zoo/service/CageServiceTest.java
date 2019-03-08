package ru.ilya.zoo.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.repository.CageRepository;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

public class CageServiceTest {

    @Mock
    private CageRepository cageRepository;

    @InjectMocks
    private CageService cageService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        reset(cageRepository);
    }

    @Test
    public void shouldDelete() {
        Cage cage = new Cage().setId(1L);
        Mockito.when(cageRepository.existsById(cage.getId())).thenReturn(true);

        cageService.delete(cage.getId());
        verify(cageRepository).existsById(cage.getId());
        verify(cageRepository).deleteById(cage.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIfNotExists_whenDeleting() {
        cageService.delete(Long.MAX_VALUE);
    }
}