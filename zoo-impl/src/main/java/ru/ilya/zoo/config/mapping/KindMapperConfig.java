package ru.ilya.zoo.config.mapping;

import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;
import ru.ilya.zoo.dto.kind.KindCreateDto;
import ru.ilya.zoo.dto.kind.KindResponseDto;
import ru.ilya.zoo.model.Kind;

@Configuration
public class KindMapperConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {
        registerCreateDtoToKindMapper(mapperFactory);
        registerKindToResponseDtoMapper(mapperFactory);
    }

    private void registerKindToResponseDtoMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(Kind.class, KindResponseDto.class)
                .byDefault()
                .register();
    }

    private void registerCreateDtoToKindMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(KindCreateDto.class, Kind.class)
                .byDefault()
                .register();
    }
}
