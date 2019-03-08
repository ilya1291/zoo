package ru.ilya.zoo.dto.kind;

import lombok.Data;

@Data
public class KindResponseDto {
    private Long id;
    private String name;
    private boolean isPredator;
}
