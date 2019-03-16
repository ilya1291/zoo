package ru.ilya.zoo.utils;

import lombok.experimental.UtilityClass;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ru.ilya.zoo.config.mapping.AnimalMapperConfig;
import ru.ilya.zoo.config.mapping.CageMapperConfig;
import ru.ilya.zoo.config.mapping.KeeperMapperConfig;
import ru.ilya.zoo.config.mapping.KindMapperConfig;

@UtilityClass
public class MapperUtils {

    public static MapperFacade createMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        new CageMapperConfig().configure(mapperFactory);
        new KindMapperConfig().configure(mapperFactory);
        new KeeperMapperConfig().configure(mapperFactory);
        new AnimalMapperConfig().configure(mapperFactory);
        return mapperFactory.getMapperFacade();
    }
}
