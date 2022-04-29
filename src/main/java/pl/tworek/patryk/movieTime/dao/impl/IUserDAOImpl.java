package pl.tworek.patryk.movieTime.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.tworek.patryk.movieTime.dao.IUserDAO;
import pl.tworek.patryk.movieTime.model.User;

import javax.persistence.NoResultException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class IUserDAOImpl implements IUserDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public User getUserByLogin(String login) {

        Session session = this.sessionFactory.openSession();
        User user = null;
        try {
            Query<User> query = session.createQuery("FROM pl.tworek.patryk.movieTime.model.User WHERE login= :login");
            query.setParameter("login", login);
            user = query.getSingleResult();
        } catch (Exception e) {
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public void updateUser(User user) {

        Session session = this.sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void persistUser(User user) {

        Session session = this.sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.tworek.patryk.movieTime.model.User");
        List<User> users = query.getResultList();
        session.close();
        return users;
    }

    @Override
    public User getUserById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.tworek.patryk.movieTime.model.User WHERE id = :id");
        query.setParameter("id", id);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("User with id" + id + " not found !");
        }
        session.close();
        return user;
    }


    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(User.Role.valueOf(resultSet.getString("role")));

        return user;
    }
}
