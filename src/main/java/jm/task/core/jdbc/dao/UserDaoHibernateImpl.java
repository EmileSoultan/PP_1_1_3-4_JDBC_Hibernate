package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final UserDaoHibernateImpl INSTANCE = new UserDaoHibernateImpl();
    private static final String CREATE_TABLE_SQL = """ 
            CREATE TABLE IF NOT EXISTS users(
            id bigint auto_increment primary key,
            name varchar(100) not null,
            last_name varchar(100) not null,
            age tinyint not null);
            """;
    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS users
            """;
    private static final String SAVE_USER_SQL = """
            INSERT INTO users(name, last_name, age)
            VALUES (?, ?, ?);
            """;
    private static final String REMOVE_USER_BY_ID_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    private static final String CLEAN_ALL_USERS_SQL = """
            DELETE FROM users
            """;
    private static final String GET_ALL_USERS_SQL = """
            SELECT * from users
                """;

    public static UserDaoHibernateImpl getInstance() {
        return INSTANCE;
    }

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (var session = HibernateUtil.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE_SQL, User.class).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана!");
        } catch (HibernateException hqlException) {
            if (transaction != null) {
                transaction.rollback();
            }
            hqlException.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (var session = HibernateUtil.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(DROP_TABLE_SQL, User.class).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена!");
        } catch (HibernateException hqlException) {
            if (transaction != null) {
                transaction.rollback();
            }
            hqlException.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (var session = HibernateUtil.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SAVE_USER_SQL, User.class)
                    .setParameter(1, name).setParameter(2, lastName)
                    .setParameter(3, age).executeUpdate();
            transaction.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (HibernateException hqlException) {
            if (transaction != null) {
                transaction.rollback();
            }
            hqlException.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (var session = HibernateUtil.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            if (id != 0) {
            session.createNativeQuery(REMOVE_USER_BY_ID_SQL, User.class)
                    .setParameter(1, id).executeUpdate();
            transaction.commit();
            }
            System.out.println("User с id " + id + " удален из базы данных");
        } catch (HibernateException hqlException) {
            if (transaction != null) {
                transaction.rollback();
            }
            hqlException.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> userList = new ArrayList<>();
        try (var session = HibernateUtil.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            userList = session.createNativeQuery(GET_ALL_USERS_SQL, User.class).list();
            transaction.commit();
        } catch (HibernateException hqlException) {
            if (transaction != null) {
                transaction.rollback();
            }
            hqlException.printStackTrace();
        }
        for (User user : userList) {
            System.out.println(user);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(CLEAN_ALL_USERS_SQL).executeUpdate();
            transaction.commit();
            System.out.println("Все пользователи удалены!");
        } catch (HibernateException hqlException) {
            if (transaction != null) {
                transaction.rollback();
            }
            hqlException.printStackTrace();
        }
    }
}
