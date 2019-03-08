package ru.ilya.zoo.dto.keeper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeeperCreateDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
