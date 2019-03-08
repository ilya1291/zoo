package ru.ilya.zoo.utils;

import lombok.experimental.UtilityClass;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ru.ilya.zoo.config.mapping.KeeperMapperConfig;
import ru.ilya.zoo.config.mapping.KindMapperConfig;

@UtilityClass
public class MapperUtils {

    public static MapperFacade createMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        new KeeperMapperConfig().configure(mapperFactory);
        new KindMapperConfig().configure(mapperFactory);
        return mapperFactory.getMapperFacade();
    }
}
