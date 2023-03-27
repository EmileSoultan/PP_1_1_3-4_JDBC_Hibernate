package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDao userDao = UserDaoJDBCImpl.getInstance();
        userDao.createUsersTable();
        userDao.saveUser("Федор", "Иванов", (byte) 32);
        userDao.saveUser("Евгений", "Смирнов", (byte) 42);
        userDao.saveUser("Елена", "Котова", (byte) 22);
        userDao.saveUser("Ирина", "Байкова", (byte) 25);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
