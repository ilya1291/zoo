package ru.ilya.zoo.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.exceptions.EntityAlreadyExistsException;
import ru.ilya.zoo.model.Role;
import ru.ilya.zoo.model.RoleName;
import ru.ilya.zoo.model.User;
import ru.ilya.zoo.repository.UserRepository;
import ru.ilya.zoo.security.JwtTokenProvider;

import java.util.List;

@Service
@Transactional
public class UserService extends BaseService<User> {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public String authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

    @Override
    @PreAuthorize("hasRole(T(ru.ilya.zoo.model.RoleName).ROLE_ADMIN.name())")
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @PreAuthorize("hasRole(T(ru.ilya.zoo.model.RoleName).ROLE_ADMIN.name())")
    public void delete(Long id) {
        super.delete(id);
    }

    @PreAuthorize("hasRole(T(ru.ilya.zoo.model.RoleName).ROLE_ADMIN.name())")
    public User register(User user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new EntityAlreadyExistsException(user.getUsername(), User.class);
        }
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException(user.getEmail(), User.class);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return create(user);
    }

    @PreAuthorize("hasRole(T(ru.ilya.zoo.model.RoleName).ROLE_ADMIN.name())")
    public User assignRole(Long userId, RoleName name) {
        User user = getOne(userId);
        Role role = roleService.getByName(name);
        user.getRoles().add(role);
        return user;
    }

    public UserService(JpaRepository<User, Long> repository,
                       RoleService roleService,
                       UserRepository userRepository, JwtTokenProvider tokenProvider,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        super(repository);
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
}
