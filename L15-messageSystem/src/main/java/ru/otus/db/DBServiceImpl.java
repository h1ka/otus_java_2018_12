package ru.otus.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.app.DBService;
import ru.otus.app.MessageSystemContext;
import ru.otus.messageSystem.*;
import ru.otus.model.User;

import java.util.List;

@Service
public class DBServiceImpl<T> implements DBService<T> {

    private final DbService dbService;
    private final Address address;
    private final MessageSystemContext context;

    @Autowired
    public DBServiceImpl(DbService dbService, MessageSystemContext context,@Value("DB") Address address) {
        this.dbService = dbService;
        this.address = address;
        this.context = context;
        context.setDbAddress(this.getAddress());
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public void init() {
        context.setDbAddress(this.getAddress());
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public List<T> loadAll() {
        return dbService.loadAll(User.class);
    }

    @Override
    public void save(Object objectData) {
        dbService.save(objectData);
    }

    @Override
    public T load(long id, Class clazz) {
        return (T) dbService.load(id,clazz);
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
