package ru.ilya.zoo.dto.cage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.ilya.zoo.dto.animal.AnimalResponseDto;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CageWithAnimalsDto extends CageResponseDto {

    private List<AnimalResponseDto> animals = new ArrayList<>();
}
