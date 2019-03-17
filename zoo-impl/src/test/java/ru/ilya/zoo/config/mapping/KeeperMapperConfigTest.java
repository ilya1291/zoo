package ru.ilya.zoo.config.mapping;

import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import ru.ilya.zoo.dto.keeper.KeeperCreateDto;
import ru.ilya.zoo.dto.keeper.KeeperResponseDto;
import ru.ilya.zoo.model.Keeper;
import ru.ilya.zoo.utils.MapperUtils;

import static org.junit.Assert.assertEquals;
import static ru.ilya.zoo.utils.TestObjects.keeper;

public class KeeperMapperConfigTest {

    private MapperFacade mapperFacade;

    @Before
    public void setUp() {
        mapperFacade = MapperUtils.createMapper();
    }

    @Test
    public void shouldMapKeeper_toKeeperResponseDto() {
        Keeper keeper = keeper().setId(1L);
        KeeperResponseDto expected = keeperResponseDto(keeper);
        KeeperResponseDto actual = mapperFacade.map(keeper, KeeperResponseDto.class);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldMapCreateDto_ToKeeper() {
        KeeperCreateDto createDto = new KeeperCreateDto("first_name", "last_name");
        Keeper expected = new Keeper()
                .setFirstName(createDto.getFirstName())
                .setLastName(createDto.getLastName());
        Keeper actual = mapperFacade.map(createDto, Keeper.class);
        assertEquals(expected, actual);
    }

    public static KeeperResponseDto keeperResponseDto(Keeper src) {
        return new KeeperResponseDto()
                .setId(src.getId())
                .setFirstName(src.getFirstName())
                .setLastName(src.getLastName());
    }
}