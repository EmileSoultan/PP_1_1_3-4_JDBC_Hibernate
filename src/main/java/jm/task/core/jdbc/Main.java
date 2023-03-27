package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;


public class Main {
    public static void main(String[] args) {
        // ���������� �������� �����
        UserDao userDao = UserDaoJDBCImpl.getInstance();
        userDao.createUsersTable();
        userDao.saveUser("�����", "������", (byte) 32);
        userDao.saveUser("�������", "�������", (byte) 42);
        userDao.saveUser("�����", "������", (byte) 22);
        userDao.saveUser("�����", "�������", (byte) 25);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
