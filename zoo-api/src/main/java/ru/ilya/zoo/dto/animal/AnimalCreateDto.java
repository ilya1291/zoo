package ru.ilya.zoo.dto.animal;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class AnimalCreateDto {

    @NotBlank
    private String name;

    private LocalDate birthDate;

    @NotNull
    @Positive
    private Long keeperId;

    @NotNull
    private String kindName;

    @NotNull
    @Positive
    private Long cageId;

}
