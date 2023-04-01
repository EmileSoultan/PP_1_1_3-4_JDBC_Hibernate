package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final UserDaoJDBCImpl INSTANCE = new UserDaoJDBCImpl();
    private static final String GET_ALL_USERS_SQL = """
            SELECT * from users
                """;
    private static final String REMOVE_USER_BY_ID_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    private static final String SAVE_USER_SQL = """
            INSERT INTO users(name, last_name, age)
            VALUES (?, ?, ?);
            """;
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
    private static final String CLEAN_ALL_USERS_SQL = """
            DELETE FROM users
            """;
    public UserDaoJDBCImpl() {
    }
    public static UserDaoJDBCImpl getInstance() {
        return INSTANCE;
    }

    public void createUsersTable() {
        try (var connection = Util.open();
             var Statement = connection.createStatement()) {
            Statement.execute(CREATE_TABLE_SQL);
            System.out.println("The table has been created!");
        } catch (SQLException sqlException) {
            System.out.println("Connection failed...");
            throw new RuntimeException();
        }
    }
    public void dropUsersTable() {
        try (var connection = Util.open();
             var Statement = connection.createStatement()) {
            Statement.execute(DROP_TABLE_SQL);
            System.out.println("The table has been deleted!");
        } catch (SQLException sqlException) {
            System.out.println("Connection failed...");
            throw new RuntimeException();
        }
    }
    public void saveUser(String name, String lastName, byte age) {
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(SAVE_USER_SQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException sqlException) {
            throw new RuntimeException();
        }
    }
    @Override
    public void removeUserById(long id) {
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("The user has been removed!");
        } catch (SQLException sqlException) {
            throw new RuntimeException();
        }
    }
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (var connection = Util.open();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(GET_ALL_USERS_SQL);

            while (resultSet.next()) {
                String name = resultSet.getString( "name");
                String lastName = resultSet.getString("last_name");
                Byte age = resultSet.getByte("age");
                userList.add(new User(name, lastName, age));
            }
            for (User user : userList) {
                System.out.println(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
    public void cleanUsersTable() {
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(CLEAN_ALL_USERS_SQL)) {
            preparedStatement.executeUpdate();
            System.out.println("All users have been removed!");
        } catch (SQLException sqlException) {
            throw new RuntimeException();
        }
    }
}
