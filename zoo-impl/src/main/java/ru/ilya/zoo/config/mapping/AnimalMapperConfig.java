package ru.ilya.zoo.config.mapping;

import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;
import ru.ilya.zoo.dto.animal.AnimalCreateDto;
import ru.ilya.zoo.dto.animal.AnimalResponseDto;
import ru.ilya.zoo.model.Animal;

@Configuration
public class AnimalMapperConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {
        registerCreateDtoToAnimalMapper(mapperFactory);
        registerAnimalToResponseDtoMapper(mapperFactory);
    }

    private void registerAnimalToResponseDtoMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(Animal.class, AnimalResponseDto.class)
                .byDefault()
                .register();
    }

    private void registerCreateDtoToAnimalMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(AnimalCreateDto.class, Animal.class)
                .field("keeperId", "keeper.id")
                .field("kindName", "kind.name")
                .byDefault()
                .register();
    }
}
