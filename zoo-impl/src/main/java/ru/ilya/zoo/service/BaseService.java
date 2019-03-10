package ru.ilya.zoo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.ilya.zoo.exceptions.EntityNotFoundException;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Transactional
@RequiredArgsConstructor
public abstract class BaseService<T> implements Service<T> {

    protected final JpaRepository<T, Long> repository;

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return repository.findAll();
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
        ParameterizedType type = (ParameterizedType)actualClass.getGenericSuperclass();
        return  (Class)type.getActualTypeArguments()[0];
    }
}
