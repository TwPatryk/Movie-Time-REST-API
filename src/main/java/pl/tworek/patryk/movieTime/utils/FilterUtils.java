package pl.tworek.patryk.movieTime.utils;

import pl.tworek.patryk.movieTime.model.Film;

import java.util.ArrayList;
import java.util.List;

public class FilterUtils {
    public static List<Film> filterOfFilms(List<Film> films, String filter) {

        if(filter == null) {
            return films;
        }
        List<Film> filmsByFilter = new ArrayList<>();;
        for (Film currentFilm : films) {
            if (currentFilm.getTitle().toUpperCase().contains(filter.toUpperCase()) || currentFilm.getDirector().toUpperCase().contains(filter.toUpperCase())) {
                filmsByFilter.add(currentFilm);
            }
        }
        return filmsByFilter;
    }
}
