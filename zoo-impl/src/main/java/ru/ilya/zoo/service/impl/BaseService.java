package ru.ilya.zoo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.exceptions.EntityNotFoundException;
import ru.ilya.zoo.service.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Transactional
@RequiredArgsConstructor
abstract class BaseService<T> implements Service<T> {

    protected final JpaRepository<T, Long> repository;

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public T getOne(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, getGenericType(this)));
    }

    @Override
    public T create(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(id, getGenericType(this));
        }
        repository.deleteById(id);
    }

    private static Class getGenericType(Object o) {
        Class actualClass = o.getClass();
        ParameterizedType type = (ParameterizedType) actualClass.getGenericSuperclass();
        return (Class) type.getActualTypeArguments()[0];
    }
}
