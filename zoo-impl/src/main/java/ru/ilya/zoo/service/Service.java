package ru.ilya.zoo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Service<T> {

    List<T> getAll();

    Page<T> getAll(Pageable pageable);

    T getOne(Long id);

    T create(T entity);

    void delete(Long id);
}
