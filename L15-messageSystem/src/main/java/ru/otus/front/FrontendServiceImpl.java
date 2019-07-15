package ru.otus.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import ru.otus.app.DBService;
import ru.otus.app.FrontendService;
import ru.otus.app.MessageSystemContext;
import ru.otus.app.MsgToDB;
import ru.otus.app.messages.MsgGetUsersAnswer;
import ru.otus.front.domain.WSMessage;
import ru.otus.messageSystem.Address;
import ru.otus.messageSystem.Message;
import ru.otus.messageSystem.MessageSystem;
import ru.otus.model.User;

import java.util.List;

@Service
public class FrontendServiceImpl implements FrontendService {
    private final Address address;
    private final MessageSystemContext context;
    private final SimpMessageSendingOperations sendingOperations;

    @Autowired
    public FrontendServiceImpl(MessageSystemContext context, @Value("Frontend") Address address, SimpMessageSendingOperations sendingOperations) {
        this.address = address;
        this.context = context;
        this.sendingOperations = sendingOperations;
        context.setFrontAddress(this.getAddress());
        context.getMessageSystem().addAddressee(this);
        context.getMessageSystem().start();
    }

    @Override
    public void init() {
        context.setFrontAddress(this.getAddress());
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void save(User user) {
        Message message = new MsgToDB(getAddress(), context.getDbAddress()) {
            @Override
            public void exec(DBService dbService) {
                dbService.save(user);
            }
        };
        getMS().sendMessage(message);
        listUsers();
    }

    @Override
    public void listUsers() {

        Message message = new MsgToDB(getAddress(), context.getDbAddress()) {
            @Override
            public void exec(DBService dbService) {
                dbService.getMS().sendMessage(
                        new MsgGetUsersAnswer(getTo(), getFrom(), dbService.loadAll()));
            }
        };
        getMS().sendMessage(message);
    }


    @Override
    public void send(List<User> users) {
        sendingOperations.convertAndSend("/topic/users", new WSMessage(users));
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
