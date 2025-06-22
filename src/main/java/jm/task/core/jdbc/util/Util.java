package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Lu14Al19Ma24!";
    private static final String URL = "jdbc:mysql://localhost:3306/task_jdbc_schema";

    public static Connection getConnection(){
       try{
       Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
       return connection;
       } catch (SQLException e){
           throw  new RuntimeException("Unable to connect to the database",e);

       }
    }
}

