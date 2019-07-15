package ru.otus.front.domain;

import ru.otus.model.User;

import java.util.List;

public class WSMessage {
    String message;
    User user;
    List<User> users;

    public WSMessage() {
    }

    public WSMessage(List<User> users) {
        this.users = users;
        user=null;
        message="users list";
    }

    public WSMessage(String message, User user, List<User> users) {
        this.message = message;
        this.user = user;
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "WSMessage{" +
                "message='" + message + '\'' +
                ", user=" + user +
                ", users=" + users +
                '}';
    }
}
