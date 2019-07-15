package ru.otus.app;

import ru.otus.model.User;
import ru.otus.messageSystem.Addressee;

import java.util.List;

public interface FrontendService extends Addressee {
    void init();
    void save(User user);
    void listUsers();
    void send(List<User> users);
}
