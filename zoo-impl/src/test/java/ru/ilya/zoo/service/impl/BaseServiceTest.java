package ru.ilya.zoo.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.TestEntity;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

public class BaseServiceTest {

    @Mock
    private JpaRepository<TestEntity, Long> repository;

    @InjectMocks
    private TestEntityService baseService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        reset(repository);
    }

    @Test
    public void shouldDelete() {
        TestEntity entity = new TestEntity().setId(1L);
        Mockito.when(repository.existsById(entity.getId())).thenReturn(true);

        baseService.delete(entity.getId());
        verify(repository).existsById(entity.getId());
        verify(repository).deleteById(entity.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIfNotExists_whenDeleting() {
        baseService.delete(Long.MAX_VALUE);
    }
}
