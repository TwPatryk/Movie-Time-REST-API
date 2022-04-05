package pl.tworek.patryk.movieTime.repository;

import pl.tworek.patryk.movieTime.model.Film;

import java.util.List;

public interface IFilmRepository {

    List<Film> getAllMovies();
    List<Film> getAllTvShows();
    List<Film> getAllFilms();
    Film rateAfilm(int grade);
    double getRate(int grade);
    void addFilm(Film film);
    void updateFilm(Film film);
}
