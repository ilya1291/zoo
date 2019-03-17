package ru.ilya.zoo.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.ilya.zoo.model.User;

import static org.junit.Assert.*;
import static ru.ilya.zoo.utils.TestObjects.user;

public class JwtTokenProviderTest {

    private final User user = user(1L);
    private JwtTokenProvider jwtTokenProvider;
    private final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(new UserDetails().setUser(user), "");
    private final String VALID_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTQ2ODU4NDI1LCJleHAiOjkyMjMzNzIwMzY4NTQ3NzV9.wxVBfBdEYRVYW72JITu6NF4qAPWeAq8KSmRq7qy5jAT8r7tjJVhq9o5onKpf6XTLiPQf-OuRddSGhdloL5CxnA";

    @Before
    public void setUp() {
        jwtTokenProvider = new JwtTokenProvider("secret", Integer.MAX_VALUE);
    }

    @Test
    public void generateToken() {
        String result = jwtTokenProvider.generateToken(authentication);
        assertNotNull(result);
        assertEquals(168, result.length());
    }

    @Test
    public void getUserIdFromJWT() {
        String token = jwtTokenProvider.generateToken(authentication);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);
        assertEquals(user.getId(), userId);
    }

    @Test
    public void testValidToken() {
        assertTrue(jwtTokenProvider.isValidToken(VALID_TOKEN));
    }

    @Test
    public void testNotValidToken() {
        String notValidToken = "not_valid_token";
        assertFalse(jwtTokenProvider.isValidToken(notValidToken));
    }

    @Test
    public void testInvalidSignatureToken() {
        char[] tokenChars = VALID_TOKEN.toCharArray();
        tokenChars[tokenChars.length - 1] = '*';
        assertFalse(jwtTokenProvider.isValidToken(new String(tokenChars)));
    }

    @Test
    public void testExpiredToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider("secret", 1);
        String expiredToken = jwtTokenProvider.generateToken(authentication);
        assertFalse(jwtTokenProvider.isValidToken(expiredToken));
    }

    @Test
    public void testEmptyClaimsToken() {
        assertFalse(jwtTokenProvider.isValidToken(""));
    }
}