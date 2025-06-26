package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;

import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Lu14Al19Ma24!";
    private static final String URL = "jdbc:mysql://localhost:3306/task_jdbc_schema";

    private static SessionFactory sessionFactory;

    //JDBC
    public static Connection getConnection(){
       try{
       Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
       return connection;
       } catch (SQLException e){
           throw  new RuntimeException("Unable to connect to the database",e);

       }
    }

    //HIBERNATE (Java-only, no XML)
    public static SessionFactory  getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperty("hibernate.connection.url", URL);
                configuration.setProperty("hibernate.connection.username", USERNAME);
                configuration.setProperty("hibernate.connection.password", PASSWORD);
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                configuration.setProperty("hibernate.hbm2dd1.auto", "update");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.jmx.enabled", "false");

                configuration.addAnnotatedClass(User.class);

                try (StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build()) {
                    sessionFactory = configuration.buildSessionFactory(registry);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Hibernate configuration failed",e);
            }
        }
        return sessionFactory;
    }

}

