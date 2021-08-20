package main.service;

import main.repository.Repository;

import java.io.Serializable;
import java.util.List;

public interface Service<T, ID extends Serializable> {
    <R extends Repository<T, ID>> R getRepository();

    void add(T object);

    void add(List<T> objects);

    void remove(T object);

    default void remove(ID id) {
        getRepository().delete(id);
    }

    default void removeAll() {
        getRepository().deleteAll();
    }

    T find(ID id);

    List<T> findAll();
}
