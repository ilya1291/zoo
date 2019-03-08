package ru.ilya.zoo.dto.cage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CageCreateDto {
    private Integer capacity;
}
