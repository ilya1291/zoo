package ru.ilya.zoo.config.mapping;

import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import ru.ilya.zoo.dto.cage.CageCreateDto;
import ru.ilya.zoo.dto.cage.CageResponseDto;
import ru.ilya.zoo.model.Cage;
import ru.ilya.zoo.utils.MapperUtils;

import static org.junit.Assert.assertEquals;

public class CageMapperConfigTest {

    private MapperFacade mapperFacade;

    @Before
    public void setUp() {
        mapperFacade = MapperUtils.createMapper();
    }

    @Test
    public void shouldMapCage_toCageResponseDto() {
        Cage cage = new Cage().setId(1L);
        CageResponseDto expected = cageResponseDto(cage);
        CageResponseDto actual = mapperFacade.map(cage, CageResponseDto.class);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldMapCreateDto_ToCage() {
        CageCreateDto createDto = new CageCreateDto(10);
        Cage expected = new Cage().setCapacity(createDto.getCapacity());
        Cage actual = mapperFacade.map(createDto, Cage.class);
        assertEquals(expected, actual);
    }

    private CageResponseDto cageResponseDto(Cage src) {
        return new CageResponseDto()
                .setId(src.getId())
                .setCapacity(src.getCapacity());
    }
}