package main.service;

import main.repository.Repository;
import net.dv8tion.jda.api.JDA;

import java.io.Serializable;
import java.util.List;

public interface Service<T, ID extends Serializable> {
    Repository<T, ID> getRepository();

    default void add(T object) {
        getRepository().save(object);
    }

    default void add(List<T> objects) {
        getRepository().save(objects);
    }

    default void remove(T object) {
        getRepository().delete(object);
    }

    default void remove(ID id) {
        getRepository().delete(id);
    }

    default void removeAll() {
        getRepository().deleteAll();
    }

    default T find(ID id) {
        return getRepository().get(id);
    }

    default List<T> findAll() {
        return getRepository().getAll();
    }
}
