package ru.ilya.zoo.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;
import ru.ilya.zoo.dto.auth.JwtDto;
import ru.ilya.zoo.dto.auth.LoginDto;
import ru.ilya.zoo.dto.auth.SignUpDto;
import ru.ilya.zoo.dto.auth.UserDto;
import ru.ilya.zoo.model.User;
import ru.ilya.zoo.service.impl.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthResourceImpl implements AuthResource {

    private final UserService userService;
    private final MapperFacade mapperFacade;

    @Override
    public JwtDto signIn(@Valid LoginDto dto) {
        log.debug("signIn - start: username: {}", dto.getUsernameOrEmail());

        String token = userService.authenticate(dto.getUsernameOrEmail(), dto.getPassword());

        log.debug("signIn - end username: {}, token: ", token);
        return new JwtDto(token);
    }

    @Override
    public UserDto signUp(@Valid SignUpDto dto) {
        log.debug("signUp - start: username = {}. email = {}", dto.getUsername(), dto.getEmail());

        User user = mapperFacade.map(dto, User.class);
        user = userService.register(user);
        UserDto result = mapperFacade.map(user, UserDto.class);

        log.debug("signUp - end: result = {}", result);
        return result;
    }
}
