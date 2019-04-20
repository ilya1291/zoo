package ru.ilya.zoo.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.RestController;
import ru.ilya.zoo.dto.user.UserRegisterDto;
import ru.ilya.zoo.dto.user.UserResponseDto;
import ru.ilya.zoo.model.RoleName;
import ru.ilya.zoo.model.User;
import ru.ilya.zoo.service.impl.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserResourceImpl implements UserResource {

    private final UserService userService;
    private final MapperFacade mapperFacade;

    @Override
    public List<UserResponseDto> getAll() {
        log.debug("getAll - start");

        List<User> users = userService.getAll();
        List<UserResponseDto> result = mapperFacade.mapAsList(users, UserResponseDto.class);

        log.debug("getAll - end: result = {}", result);
        return result;
    }

    @Override
    public UserResponseDto register(@Valid UserRegisterDto dto) {
        log.debug("register - start: username = {}. email = {}", dto.getUsername(), dto.getEmail());

        User user = mapperFacade.map(dto, User.class);
        user = userService.register(user);
        UserResponseDto result = mapperFacade.map(user, UserResponseDto.class);

        log.debug("register - end: result = {}", result);
        return result;
    }

    @Override
    public UserResponseDto assignRole(Long userId, String roleName) {
        log.debug("assignRole - start: userId = {}. roleName = {}", userId, roleName);

        RoleName name = RoleName.valueOf(roleName);
        User user = userService.assignRole(userId, name);
        UserResponseDto result = mapperFacade.map(user, UserResponseDto.class);

        log.debug("assignRole - end: result = {}", result);
        return result;
    }

    @Override
    public void deleteById(Long userId) {
        log.debug("deleteById - start: userId = {}", userId);

        userService.delete(userId);

        log.debug("deleteById - end: userId = {}", userId);
    }
}
