package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Hanna", "Le", (byte) 7);
        userService.saveUser("James", "Best", (byte)50);
        userService.saveUser("Joanna", "Petrina", (byte)45);
        userService.saveUser("Mila", "Basova", (byte)13);

        for (User user : userService.getAllUsers()){
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();// implement algorithm here
    }
}
