package ru.ilya.zoo.config.mapping;

import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;
import ru.ilya.zoo.dto.keeper.KeeperCreateDto;
import ru.ilya.zoo.dto.keeper.KeeperResponseDto;
import ru.ilya.zoo.model.Keeper;

@Configuration
public class KeeperMapperConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {
        registerCreateDtoToKeeperMapper(mapperFactory);
        registerKeeperToResponseDtoMapper(mapperFactory);
    }

    private void registerKeeperToResponseDtoMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(Keeper.class, KeeperResponseDto.class)
                .byDefault()
                .register();
    }

    private void registerCreateDtoToKeeperMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(KeeperCreateDto.class, Keeper.class)
                .byDefault()
                .register();
    }
}
