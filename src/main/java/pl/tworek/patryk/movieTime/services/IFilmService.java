package pl.tworek.patryk.movieTime.services;

import pl.tworek.patryk.movieTime.model.Film;

import java.util.List;

public interface IFilmService {
    AddFilmResult addFilm(Film film);
    String filePathGenerator();
    String viewPathModifier(String filePath);
    Film getFilmById(int id);
    List<Film> getFilmsByCategoryWithFilter(String category);

    enum AddFilmResult {
        FILM_ADDED,
        FILM_UPDATED
    }
}
