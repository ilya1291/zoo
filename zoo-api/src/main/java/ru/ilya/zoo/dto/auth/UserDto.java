package ru.ilya.zoo.dto.auth;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
}
