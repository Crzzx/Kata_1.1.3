package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    private final String DB_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(64), lastName VARCHAR(64), age TINYINT(3))";
    private final String DB_DROP_TABLE = "DROP TABLE IF EXISTS users";
    private final String DB_CLEAN_USERS = "DELETE FROM users";


    SessionFactory sessionFactory = Util.getSessionFactory();
    Session session = sessionFactory.getCurrentSession();
    Transaction transaction;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(DB_CREATE_TABLE).executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(DB_DROP_TABLE).executeUpdate();
            session.getTransaction().commit();
        } catch (Throwable e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("User с именем " + name + " добавлен в базу данных");

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {

        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("From User");

        List<User> resultList = q.list();
        try {

            System.out.println("num of user:" + resultList.size());

            for (User next : resultList) {
                System.out.println("next user: " + next);
            }
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return resultList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(DB_CLEAN_USERS).executeUpdate();
            session.getTransaction().commit();
            System.out.println("База данных очищена.");
        } catch (Throwable e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
