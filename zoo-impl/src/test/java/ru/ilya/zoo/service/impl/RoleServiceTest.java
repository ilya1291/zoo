package ru.ilya.zoo.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Role;
import ru.ilya.zoo.model.RoleName;
import ru.ilya.zoo.repository.RoleRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        reset(roleRepository);
    }

    @Test
    public void shouldGetByName() {
        Role expected = new Role().setName(RoleName.ROLE_ADMIN);
        when(roleRepository.findByName(RoleName.ROLE_ADMIN)).thenReturn(Optional.of(expected));
        Role actual = roleService.getByName(RoleName.ROLE_ADMIN);
        assertEquals(expected, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrow_WhenRoleNotFound() {
        roleService.getByName(RoleName.ROLE_ADMIN);
    }
}