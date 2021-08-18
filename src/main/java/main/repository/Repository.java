package main.repository;

import java.util.List;

public interface Repository<T> {
    void save(T object);
    void save(List<T> objects);
    void delete(T object);
    void delete(int id);
    void deleteAll();
    T get(int id);
    List<T> getAll();
}
