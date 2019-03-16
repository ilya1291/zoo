package ru.ilya.zoo.dto.animal;

import lombok.Data;
import ru.ilya.zoo.dto.keeper.KeeperResponseDto;
import ru.ilya.zoo.dto.kind.KindResponseDto;

import java.time.LocalDate;

@Data
public class AnimalResponseDto {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private Long cageId;
    private KindResponseDto kind;
    private KeeperResponseDto keeper;
}
