package ru.otus.app.messages;


import ru.otus.app.FrontendService;
import ru.otus.app.MsgToFrontend;
import ru.otus.messageSystem.*;
import ru.otus.model.User;

import java.util.List;


public class MsgGetUsersAnswer extends MsgToFrontend {
    private final List<User> users;

    public MsgGetUsersAnswer(Address from, Address to, List<User> users) {
        super(from, to);
        this.users = users;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.send(users);
    }
}

