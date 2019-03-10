package ru.ilya.zoo.service;

import java.util.List;

public interface Service<T> {

    List<T> getAll();

    T create(T entity);

    void delete(Long id);
}
