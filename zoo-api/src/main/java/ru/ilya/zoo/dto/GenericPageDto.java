package ru.ilya.zoo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericPageDto<T> {
    private List<T> data;
    private long total;
}
