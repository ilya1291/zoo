package ru.ilya.zoo.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.ilya.zoo.dto.auth.JwtDto;
import ru.ilya.zoo.dto.auth.LoginDto;
import ru.ilya.zoo.service.impl.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthResourceImpl implements AuthResource {

    private final UserService userService;

    @Override
    public JwtDto login(@Valid LoginDto dto) {
        log.debug("login - start: username: {}", dto.getUsernameOrEmail());

        String token = userService.authenticate(dto.getUsernameOrEmail(), dto.getPassword());

        log.debug("login - end username: {}, token: ", token);
        return new JwtDto(token);
    }
}
