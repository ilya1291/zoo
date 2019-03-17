package ru.ilya.zoo.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.ilya.zoo.model.User;

import static org.junit.Assert.assertEquals;
import static ru.ilya.zoo.utils.TestObjects.user;

public class SecurityServiceTest {

    private final User user = user(1L);
    private SecurityService securityService;

    @Before
    public void setUp() {
        UserDetails userDetails = new UserDetails().setUser(user);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, "password"));
        securityService = new SecurityService();
    }

    @Test
    public void getCurrentUserId() {
        Long resultUserId = securityService.getCurrentUserId();
        assertEquals(user.getId(), resultUserId);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrow_WhenUserNotFoundInContext() {
        SecurityContextHolder.getContext().setAuthentication(null);
        securityService.getCurrentUserId();
    }
}