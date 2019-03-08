package ru.ilya.zoo.model.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.function.BiConsumer;

@UtilityClass
public class BidirectionalUtils {

    public <T, L> T set(T entity, Collection<L> currentEntityList, Collection<L> listToSet, BiConsumer<L, T> bidirectionalPropertySetter) {
        if (listToSet != currentEntityList) {
            currentEntityList.clear();
            if (CollectionUtils.isEmpty(listToSet)) {
                return entity;
            }
            currentEntityList.addAll(listToSet);
        }
        currentEntityList.forEach(subEntity -> bidirectionalPropertySetter.accept(subEntity, entity));

        return entity;
    }
}
