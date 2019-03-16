package ru.ilya.zoo.config.mapping;

import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;
import ru.ilya.zoo.dto.cage.CageCreateDto;
import ru.ilya.zoo.dto.cage.CageResponseDto;
import ru.ilya.zoo.dto.cage.CageWithAnimalsDto;
import ru.ilya.zoo.model.Cage;

@Configuration
public class CageMapperConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {
        registerCageWithAnimalsDto(mapperFactory);
        registerCreateDtoToCageMapper(mapperFactory);
        registerCageToResponseDtoMapper(mapperFactory);
    }

    private void registerCageToResponseDtoMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(Cage.class, CageResponseDto.class)
                .byDefault()
                .register();
    }

    private void registerCreateDtoToCageMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(CageCreateDto.class, Cage.class)
                .byDefault()
                .register();
    }

    private void registerCageWithAnimalsDto(MapperFactory mapperFactory) {
        mapperFactory.classMap(Cage.class, CageWithAnimalsDto.class)
                .byDefault()
                .register();
    }
}
