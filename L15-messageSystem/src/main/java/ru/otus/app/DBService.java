package ru.otus.app;


import ru.otus.messageSystem.Addressee;

import java.util.List;

public interface DBService<T> extends Addressee {
    void init();
    void save(T objectData);
    <T> T load(long id, Class<T> clazz);
    List<T> loadAll();
}
