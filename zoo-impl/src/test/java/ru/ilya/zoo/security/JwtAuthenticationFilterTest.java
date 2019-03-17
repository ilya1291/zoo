package ru.ilya.zoo.security;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import ru.ilya.zoo.model.User;
import ru.ilya.zoo.service.impl.UserDetailsServiceImpl;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static ru.ilya.zoo.utils.TestObjects.user;

public class JwtAuthenticationFilterTest {

    private final User user = user(1L);
    private final UserDetails userDetails = new UserDetails().setUser(user);

    private JwtTokenProvider tokenProvider;
    private JwtAuthenticationFilter jwtFilter;


    @Before
    public void setup() {
        tokenProvider = new JwtTokenProvider("secret", 60000);
        jwtFilter = new JwtAuthenticationFilter();
        ReflectionTestUtils.setField(jwtFilter, "tokenProvider", tokenProvider);

        UserDetailsServiceImpl userDetailsService = Mockito.mock(UserDetailsServiceImpl.class);
        when(userDetailsService.loadUserById(user.getId())).thenReturn(userDetails);
        ReflectionTestUtils.setField(jwtFilter, "userDetailsService", userDetailsService);

        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void shouldPassJWTFilterSuccessful() throws Exception {
        String jwt = tokenProvider.generateToken(new UsernamePasswordAuthenticationToken(userDetails, null, emptyList()));
        MockHttpServletRequest request = createMockServletRequest(jwt);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(SecurityContextHolder.getContext().getAuthentication().getName(), user.getUsername());
    }

    @Test
    public void shouldJWTFilterInvalidToken() throws Exception {
        String incorrectToken = "incorrect_token";
        MockHttpServletRequest request = createMockServletRequest(incorrectToken);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilter(request, response, filterChain);

        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void shouldJWTFilterMissingAuthorization() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtFilter.doFilter(request, response, new MockFilterChain());
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void shouldJWTFilterMissingToken() throws Exception {
        MockHttpServletRequest request = createMockServletRequest("");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtFilter.doFilter(request, response, new MockFilterChain());
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    private MockHttpServletRequest createMockServletRequest(String jwt) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        request.setRequestURI("/api/animals");
        return request;
    }

}