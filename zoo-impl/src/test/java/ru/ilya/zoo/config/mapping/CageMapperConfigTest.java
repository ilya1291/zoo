package ru.ilya.zoo.config.mapping;

import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import ru.ilya.zoo.dto.cage.CageCreateDto;
import ru.ilya.zoo.dto.cage.CageResponseDto;
import ru.ilya.zoo.dto.cage.CageWithAnimalsDto;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.utils.MapperUtils;
import ru.ilya.zoo.utils.TestObjects;

import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static ru.ilya.zoo.utils.TestObjects.cage;
import static ru.ilya.zoo.utils.TestObjects.cageForPredators;

public class CageMapperConfigTest {

    private MapperFacade mapperFacade;

    @Before
    public void setUp() {
        mapperFacade = MapperUtils.createMapper();
    }

    @Test
    public void shouldMapCage_toCageResponseDto() {
        Cage cage = cageForPredators(10);
        CageResponseDto expected = cageResponseDto(cage);
        CageResponseDto actual = mapperFacade.map(cage, CageResponseDto.class);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldMapCreateDto_ToCage() {
        CageCreateDto createDto = new CageCreateDto(10, true);
        Cage expected = cageForPredators(createDto.getCapacity());
        Cage actual = mapperFacade.map(createDto, Cage.class);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldMapCage_ToCageWithAnimalsDto() {
        Animal animal = TestObjects.animal();
        Cage cage = cage()
                .setId(1L)
                .setAnimals(Collections.singletonList(animal));

        CageWithAnimalsDto expected = cageWithAnimalsDto(cage);
        CageWithAnimalsDto actual = mapperFacade.map(cage, CageWithAnimalsDto.class);

        assertEquals(expected, actual);
    }

    private CageResponseDto cageResponseDto(Cage src) {
        return new CageResponseDto()
                .setId(src.getId())
                .setForPredators(src.isForPredators())
                .setCapacity(src.getCapacity());
    }

    private CageWithAnimalsDto cageWithAnimalsDto(Cage src) {
        return (CageWithAnimalsDto) new CageWithAnimalsDto()
                .setAnimals(src.getAnimals().stream()
                        .map(AnimalMapperConfigTest::animalResponseDto)
                        .collect(Collectors.toList())
                )
                .setId(src.getId())
                .setCapacity(src.getCapacity());

    }
}