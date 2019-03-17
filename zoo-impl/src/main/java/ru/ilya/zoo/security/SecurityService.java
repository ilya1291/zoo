package ru.ilya.zoo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.model.User;

import static java.util.Optional.ofNullable;

@Service
@Transactional
public class SecurityService {

    public Long getCurrentUserId() {
        return ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(authentication -> (UserDetails) authentication)
                .map(UserDetails::getUser)
                .map(User::getId)
                .orElseThrow(() -> new IllegalStateException("Could not find user in current context"));
    }
}
