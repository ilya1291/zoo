package ru.ilya.zoo.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ilya.zoo.dto.auth.JwtDto;
import ru.ilya.zoo.dto.auth.LoginDto;
import ru.ilya.zoo.dto.auth.SignUpDto;
import ru.ilya.zoo.dto.auth.UserDto;

import javax.validation.Valid;

@Api(value = "Auth", description = "Auth resource")
@RequestMapping("auth")
public interface AuthResource {

    @PostMapping("/signin")
    @ApiOperation("Sign in")
    @ResponseStatus(HttpStatus.OK)
    JwtDto signIn(@Valid @RequestBody LoginDto dto);

    @PostMapping("/signup")
    @ApiOperation("Sign up")
    @ResponseStatus(HttpStatus.CREATED)
    UserDto signUp(@Valid @RequestBody SignUpDto dto);
}
