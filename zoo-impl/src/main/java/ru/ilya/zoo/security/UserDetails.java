package ru.ilya.zoo.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import ru.ilya.zoo.model.User;

import java.util.Collection;

@Getter
@Setter
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
