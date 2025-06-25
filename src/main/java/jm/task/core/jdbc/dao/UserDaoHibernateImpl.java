package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            String sql = """
                    CREATE TABLE IF NOT EXISTS users (
                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                     name VARCHAR(50),
                     lastname VARCHAR(50),
                     age TINYINT
                     )
                    """;
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Table was created successfully.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            System.out.println("Table was dropped successfully.");
        }catch (Exception e){
        e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.persist(user);

            transaction.commit();
            System.out.println("User with the name " + name + "has been added to database.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            if(user != null){
                session.remove(user);
            }
            transaction.commit();
            System.out.println("User was removed successfully.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Session session = Util.getSessionFactory().openSession()){
            users = session.createNativeQuery("SELECT * FROM users", User.class).list();
            System.out.println("All users were retrieved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
            System.out.println("All users have been successfully removed from the table.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
