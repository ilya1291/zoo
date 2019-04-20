package ru.ilya.zoo.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ilya.zoo.dto.user.UserRegisterDto;
import ru.ilya.zoo.dto.user.UserResponseDto;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Users", description = "User resource for admin")
@RequestMapping("admin/users")
public interface UserResource {

    @GetMapping
    @ApiOperation("Get all user")
    @ResponseStatus(HttpStatus.OK)
    List<UserResponseDto> getAll();

    @PostMapping
    @ApiOperation("Registering new user")
    @ResponseStatus(HttpStatus.CREATED)
    UserResponseDto register(@Valid @RequestBody UserRegisterDto dto);


    @PutMapping("/{userId}/roles/{roleName}")
    @ApiOperation("Assign role on the user")
    @ResponseStatus(HttpStatus.OK)
    UserResponseDto assignRole(@PathVariable("userId") Long userId,
                               @PathVariable("roleName") String roleName);

    @DeleteMapping("/{userId}")
    @ApiOperation("Registering new user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("userId") Long userId);
}
