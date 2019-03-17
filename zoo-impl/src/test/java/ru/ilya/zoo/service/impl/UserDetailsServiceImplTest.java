package ru.ilya.zoo.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.ilya.zoo.model.Role;
import ru.ilya.zoo.model.RoleName;
import ru.ilya.zoo.model.User;
import ru.ilya.zoo.repository.UserRepository;
import ru.ilya.zoo.security.UserDetails;
import ru.ilya.zoo.utils.TestObjects;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    private User user;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        user = TestObjects.user();
        user.setRoles(Collections.singletonList(new Role().setName(RoleName.ROLE_MANAGER)));
        when(userRepository.findByUsernameOrEmail(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.findByUsernameOrEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    }

    @Test
    public void loadUserByUsername() {
        UserDetails result = userDetailsService.loadUserByUsername(user.getUsername());

        assertNotNull(result);
        User resultUser = result.getUser();
        assertNotNull(resultUser);
        assertEquals(user, resultUser);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrow_whenLoadingNonExistingUser() {
        userDetailsService.loadUserByUsername("nonexisting_user");
    }

    @Test
    public void loadUserById() {
        UserDetails result = userDetailsService.loadUserById(user.getId());

        assertNotNull(result);
        User resultUser = result.getUser();
        assertNotNull(resultUser);
        assertEquals(user, resultUser);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrow_whenLoadingNonExistingUserById() {
        userDetailsService.loadUserById(Long.MAX_VALUE);
    }
}