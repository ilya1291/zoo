package ru.ilya.zoo.config.mapping;

import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import ru.ilya.zoo.dto.kind.KindCreateDto;
import ru.ilya.zoo.dto.kind.KindResponseDto;
import ru.ilya.zoo.model.Kind;
import ru.ilya.zoo.utils.MapperUtils;

import static org.junit.Assert.assertEquals;
import static ru.ilya.zoo.utils.TestUtils.kind;

public class KindMapperConfigTest {

    private MapperFacade mapperFacade;

    @Before
    public void setUp() {
        mapperFacade = MapperUtils.createMapper();
    }

    @Test
    public void shouldMapKind_toKindResponseDto() {
        Kind kind = kind().setId(1L);
        KindResponseDto expected = kindResponseDto(kind);
        KindResponseDto actual = mapperFacade.map(kind, KindResponseDto.class);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldMapCreateDto_ToKind() {
        KindCreateDto createDto = new KindCreateDto("name", true);
        Kind expected = new Kind()
                .setName(createDto.getName())
                .setPredator(createDto.isPredator());
        Kind actual = mapperFacade.map(createDto, Kind.class);
        assertEquals(expected, actual);
    }

    private KindResponseDto kindResponseDto(Kind kind) {
        return new KindResponseDto()
                .setId(kind.getId())
                .setName(kind.getName())
                .setPredator(kind.isPredator());
    }
}