package ru.otus.front.config;

import org.springframework.stereotype.Component;
import ru.otus.db.DbService;
import ru.otus.db.DbServiceHibernate;
import ru.otus.model.User;

import java.sql.SQLException;

@Component
public class DBInitialize {
    private static final String URL = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1";

    public void init() throws SQLException {

        User user = new User(1l, "WNika", 23);
        User notUser = new User(1l, "Nika", 25);
        User user2 = new User(2l, "User", 25);

        DbService<User> dbService = new DbServiceHibernate<>();
        dbService.save(user);
        dbService.save(notUser);
        dbService.save(user2);

    }

}
