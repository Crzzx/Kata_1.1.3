package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Alex", "Alexandrov", (byte) 33);
        userService.saveUser("Vlad", "Palenkov", (byte) 23);
        userService.saveUser("Ivan", "Ivanov", (byte) 43);
        userService.saveUser("Pavel", "Pavlov", (byte) 53);

        List<User> users = userService.getAllUsers();
        System.out.println(users);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
