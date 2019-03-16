package ru.ilya.zoo.config.mapping;

import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import ru.ilya.zoo.dto.animal.AnimalCreateDto;
import ru.ilya.zoo.dto.animal.AnimalResponseDto;
import ru.ilya.zoo.model.Animal;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.model.Kind;
import ru.ilya.zoo.utils.MapperUtils;
import ru.ilya.zoo.utils.TestUtils;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static ru.ilya.zoo.config.mapping.KeeperMapperConfigTest.keeperResponseDto;
import static ru.ilya.zoo.config.mapping.KindMapperConfigTest.kindResponseDto;

public class AnimalMapperConfigTest {

    private MapperFacade mapperFacade;

    @Before
    public void setUp() {
        mapperFacade = MapperUtils.createMapper();
    }

    @Test
    public void shouldMapAnimal_toAnimalResponseDto() {
        Animal animal = TestUtils.animal();
        AnimalResponseDto expected = animalResponseDto(animal);
        AnimalResponseDto actual = mapperFacade.map(animal, AnimalResponseDto.class);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldMapCreateDto_ToAnimal() {
        AnimalCreateDto createDto = new AnimalCreateDto()
                .setName("name")
                .setBirthDate(LocalDate.now())
                .setCageId(1L)
                .setKeeperId(2L)
                .setKindId(3L);
        Animal expected = animal(createDto);
        Animal actual = mapperFacade.map(createDto, Animal.class);
        assertEquals(expected, actual);
        assertEquals(expected.getKind().getId(), actual.getKind().getId());
        assertEquals(expected.getKeeper().getId(), actual.getKeeper().getId());
        assertEquals(expected.getCageId(), actual.getCageId());
    }

    public static AnimalResponseDto animalResponseDto(Animal src) {
        return new AnimalResponseDto()
                .setId(src.getId())
                .setName(src.getName())
                .setBirthDate(src.getBirthDate())
                .setCageId(src.getCageId())
                .setKeeper(keeperResponseDto(src.getKeeper()))
                .setKind(kindResponseDto(src.getKind()));
    }

    private static Animal animal(AnimalCreateDto src) {
        return new Animal()
                .setName(src.getName())
                .setBirthDate(src.getBirthDate())
                .setKeeper(new Keeper().setId(src.getKeeperId()))
                .setCageId(src.getCageId())
                .setKind(new Kind().setId(src.getKindId()));
    }
}