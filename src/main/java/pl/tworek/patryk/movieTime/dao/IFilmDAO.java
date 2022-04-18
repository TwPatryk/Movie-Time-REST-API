package pl.tworek.patryk.movieTime.dao;

import pl.tworek.patryk.movieTime.model.Film;

import java.util.List;

public interface IFilmDAO {
    Film getFilmByTitle(String title);
    void updateFilm(Film film);
    void persistFilm(Film film);
    void deleteFilm(Film film);
    Film getFilmById(int id);
    List<Film> getFilmsByCategory(Film.Category category);
    List<Film> getAllFilms();
}
