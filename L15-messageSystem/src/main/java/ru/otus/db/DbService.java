package ru.otus.db;

import java.util.List;

public interface DbService<T> {
    void save(T objectData);
    <T> T load(long id, Class<T> clazz);
    List<T> loadAll(Class<T> clazz);

}