package ru.ilya.zoo.service.impl;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static ru.ilya.zoo.utils.TestObjects.animal;
import static ru.ilya.zoo.utils.TestObjects.keeper;
import static ru.ilya.zoo.utils.TestObjects.kind;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.repository.AnimalRepository;

public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        reset(animalRepository);
    }

    @Test
    public void getRandom() {
        Animal expected = animal(kind(), 1L, keeper());
        when(animalRepository.count()).thenReturn(10L);
        when(animalRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(singletonList(expected)));

        Animal actual = animalService.getRandom();

        assertEquals(expected, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowNotFound_WhenCountZero() {
        when(animalRepository.count()).thenReturn(0L);
        animalService.getRandom();
    }
}