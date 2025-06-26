package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age TINYINT, " +
                "PRIMARY KEY (id))";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("Table was created successfully or already exists.");
        } catch (SQLException e) {
            System.err.println("Failed to create table:");
            e.printStackTrace();
        }
    }




    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("Table was dropped successfully or did not exist.");
        } catch (SQLException e) {
            System.err.println("Failed to drop table:");
            e.printStackTrace();
        }
    }



    public void saveUser(String name, String lastName, byte age) {
         String sql = "INSERT INTO users (name, lastname, age) VALUES (?,?,?)";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User was saved successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to save user:");
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String sql ="DELETE FROM users WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.executeUpdate(sql);
            System.out.println("User with id " + id +  "was removed successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to remove user:");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet res = preparedStatement.executeQuery();
                while (res.next()){
                    User user = new User();
                    user.setId(res.getLong("id"));
                    user.setName(res.getString("name"));
                    user.setLastName(res.getString("lastname"));
                    user.setAge(res.getByte("age"));
                    users.add(user);
                }
                System.out.println("Users were selected successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to select user:");
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("All users were deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to delete users:");
            e.printStackTrace();
        }

    }
}
