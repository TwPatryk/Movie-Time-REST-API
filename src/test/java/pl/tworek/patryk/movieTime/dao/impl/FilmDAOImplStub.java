package pl.tworek.patryk.movieTime.dao.impl;

import pl.tworek.patryk.movieTime.dao.IFilmDAO;
import pl.tworek.patryk.movieTime.model.Film;

import java.util.List;

public class FilmDAOImplStub implements IFilmDAO {
    @Override
    public Film getFilmByTitle(String title) {
        return null;
    }

    @Override
    public void updateFilm(Film film) {

    }

    @Override
    public void persistFilm(Film film) {

    }

    @Override
    public void deleteFilm(Film film) {

    }

    @Override
    public Film getFilmById(int id) {
        return null;
    }

    @Override
    public List<Film> getFilmsByCategory(Film.Category category) {
        return null;
    }

    @Override
    public List<Film> getAllFilms() {
        return null;
    }
}
