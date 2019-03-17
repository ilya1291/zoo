package ru.ilya.zoo.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import ru.ilya.zoo.exceptions.EntityAlreadyExistsException;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.model.Role;
import ru.ilya.zoo.model.RoleName;
import ru.ilya.zoo.model.User;
import ru.ilya.zoo.repository.UserRepository;
import ru.ilya.zoo.security.JwtTokenProvider;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static ru.ilya.zoo.utils.TestObjects.user;

public class UserServiceTest {

    private User user;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private JwtTokenProvider tokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        user = user().setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    }

    @After
    public void tearDown() {
        reset(userRepository, roleService, tokenProvider, authenticationManager);
    }

    @Test
    public void shouldGetById() {
        User result = userService.getOne(user.getId());

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrow_WhenUserNotFound() {
        userService.getOne(Long.MAX_VALUE);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void shouldThrow_WhenRegistrationUserWithExistingUsername() {
        User existingUser = user("already_existing", "already_existing");
        when(userRepository.existsByUsername(existingUser.getUsername())).thenReturn(true);
        userService.register(existingUser);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void shouldThrow_WhenRegistrationUserWithExistingEmail() {
        User existingUser = user("already_existing", "already_existing");
        when(userRepository.existsByEmail(existingUser.getEmail())).thenReturn(true);
        userService.register(existingUser);
    }

    @Test
    public void testAuthentication() {
        String expectedToken = "token";
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);
        when(tokenProvider.generateToken(auth)).thenReturn(expectedToken);

        String token = userService.authenticate(user.getUsername(), user.getPassword());
        assertEquals(expectedToken, token);
    }

    @Test
    public void testAssignRole() {
        Role role = new Role()
                .setId(1L)
                .setName(RoleName.ROLE_ADMIN);
        when(roleService.getByName(RoleName.ROLE_ADMIN)).thenReturn(role);
        User result = userService.assignRole(1L, RoleName.ROLE_ADMIN);

        assertTrue(result.getRoles().contains(role));
    }
}