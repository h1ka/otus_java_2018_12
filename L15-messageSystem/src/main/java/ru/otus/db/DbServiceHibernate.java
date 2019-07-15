package ru.otus.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import ru.otus.model.User;

import java.nio.file.Path;
import java.util.List;


@Repository
public class DbServiceHibernate<T> implements DbService<T> {
    private final SessionFactory sessionFactory;

    private static final String URL = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1";

    public DbServiceHibernate() {
        System.out.println("qwerty " + Path.of("hibernate.cfg.xml").getRoot());
        Configuration configuration = new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
                .setProperty("hibernate.connection.url", URL)
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "create");


        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(User.class)
                .getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    @Override
    public void save(T objectData) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(objectData);
            session.getTransaction().commit();
        }
    }

    @Override
    public <T> T load(long id, Class<T> clazz) {
        try (Session session = sessionFactory.openSession()) {
            T selected = session.get(clazz, id);
            System.out.println(">>>>>>>>>>> selected:" + selected);
            return selected;
        }
    }

    @Override
    public List<T> loadAll(Class<T> clazz) {
        try (Session session = sessionFactory.openSession()) {
            String tableName = clazz.getSimpleName();
            String sql = String.format("SELECT a FROM %s a", tableName);
            List<T> resultList = session.createQuery(sql, clazz).getResultList();
            return resultList;

        }
    }
}
