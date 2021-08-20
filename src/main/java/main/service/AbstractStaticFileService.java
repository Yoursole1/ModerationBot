package main.service;

import lombok.Getter;
import main.repository.StaticFileRepository;

import java.io.Serializable;
import java.util.List;

@Getter
public abstract class AbstractStaticFileService<T, ID extends Serializable> implements Service<T, ID> {
    private final StaticFileRepository repository;

    AbstractStaticFileService(StaticFileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(T object) {

    }

    @Override
    public void add(List<T> objects) {

    }

    @Override
    public void remove(T object) {

    }

    @Override
    public T find(ID id) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return null;
    }
}
