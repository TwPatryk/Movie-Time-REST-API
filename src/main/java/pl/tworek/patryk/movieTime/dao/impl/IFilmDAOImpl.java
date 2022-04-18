package pl.tworek.patryk.movieTime.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.tworek.patryk.movieTime.dao.IFilmDAO;
import pl.tworek.patryk.movieTime.model.Film;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class IFilmDAOImpl implements IFilmDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Film getFilmByTitle(String title) {

        Session session = null;
        Film film = null;

        session = this.sessionFactory.openSession();
        Query<Film> query = session.createQuery("FROM pl.tworek.patryk.movieTime.model.Film WHERE title= :title");
        query.setParameter("title", title);
        try {
            film = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Film not found !");
        }
        session.close();

        return film;
    }

    @Override
    public void updateFilm(Film film) {

        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(film);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void persistFilm(Film film) {

        Session session = this.sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(film);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public void deleteFilm(Film film) {

        Session session = this.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(film);
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Film getFilmById(int id) {

        Session session = this.sessionFactory.openSession();
        Film film = null;

        try {
            Query<Film> query = session.createQuery("FROM pl.tworek.patryk.movieTime.model.Film WHERE id= :id");
            query.setParameter("id", id);
            film = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Film not found !");
        } finally {
            session.close();
        }
        return film;
    }

    @Override
    public List<Film> getFilmsByCategory(Film.Category category) {

        Session session = this.sessionFactory.openSession();

        Query<Film> query = session.createQuery("FROM pl.tworek.patryk.movieTime.model.Film WHERE category  = :category");
        query.setParameter("category", category);
        List<Film> filmList = query.getResultList();
        session.close();

        return filmList;
    }

    @Override
    public List<Film> getAllFilms() {

        Session session = this.sessionFactory.openSession();

        Query<Film> query = session.createQuery("FROM pl.tworek.patryk.movieTime.model.Film");
        List<Film> filmList = query.getResultList();

        session.close();

        return filmList;
    }

    private Film mapResultSetToFilm(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getInt("id"));
        film.setCategory(Film.Category.valueOf(resultSet.getString("category")));
        film.setDirector(resultSet.getString("director"));
        film.setGenre(resultSet.getString("genre"));
        film.setLength(resultSet.getString("length"));
        film.setProductionYear(resultSet.getString("productionYear"));
        film.setTitle(resultSet.getString("title"));
        film.setVoteCount(resultSet.getInt("voteCount"));
        film.setRate(resultSet.getDouble("rate"));
        film.setRateSum(resultSet.getDouble("rateSum"));
        film.setFilePath(resultSet.getString("filePath"));

        return film;
    }
}
