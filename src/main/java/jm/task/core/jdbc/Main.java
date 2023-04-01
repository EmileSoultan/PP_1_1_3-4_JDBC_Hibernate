package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;


public class Main {
    public static void main(String[] args) {
        // ���������� �������� �����
//        UserDao userDao = UserDaoJDBCImpl.getInstance();
//        userDao.createUsersTable();
//        userDao.saveUser("Иван", "Смирнов", (byte) 32);
//        userDao.saveUser("Галина", "Иванова", (byte) 42);
//        userDao.saveUser("Дмитрий", "Фролов", (byte) 22);
//        userDao.saveUser("Жанна", "Сидорова", (byte) 25);
//        userDao.getAllUsers();
//        userDao.cleanUsersTable();

        //HibernateUtil.buildSessionFactory();
        UserDao userDao = UserDaoHibernateImpl.getInstance();
        userDao.dropUsersTable();
        userDao.createUsersTable();
        userDao.saveUser("Надежда", "Петрова", (byte) 44);
        userDao.saveUser("Иван", "Смирнов", (byte) 32);
        userDao.saveUser("Галина", "Иванова", (byte) 42);
        userDao.saveUser("Дмитрий", "Фролов", (byte) 22);
        userDao.saveUser("Жанна", "Сидорова", (byte) 25);
        userDao.removeUserById(1);
        userDao.getAllUsers();
    }
}
