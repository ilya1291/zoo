package ru.ilya.zoo.service.impl;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static ru.ilya.zoo.utils.TestObjects.animal;
import static ru.ilya.zoo.utils.TestObjects.cage;
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
import ru.ilya.zoo.exceptions.BadRequestException;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.repository.AnimalRepository;

import java.util.Optional;

public class AnimalServiceTest {

    @Mock
    private CageService cageService;

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

    @Test(expected = BadRequestException.class)
    public void shouldThrow_whenMovingHerbivoreToPredatorsCage() {
        Animal herbivoreAnimal = animal().setKind(kind());
        Cage predatorsCage = cage().setForPredators(true);

        when(cageService.getOne(anyLong())).thenReturn(predatorsCage);
        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(herbivoreAnimal));
        animalService.moveToCage(1L, 1L);
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrow_whenMovingAnimalToFullCage() {
        Cage cage = cage(1)
                .setAnimals(singletonList(animal()));

        when(cageService.getOne(anyLong())).thenReturn(cage);
        when(animalRepository.findById(anyLong())).thenReturn(Optional.of(animal()));
        animalService.moveToCage(1L, 2L);
    }
}