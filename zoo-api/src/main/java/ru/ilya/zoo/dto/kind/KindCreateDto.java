package ru.ilya.zoo.dto.kind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KindCreateDto {

    @NotBlank
    private String name;

    private boolean isPredator;
}
