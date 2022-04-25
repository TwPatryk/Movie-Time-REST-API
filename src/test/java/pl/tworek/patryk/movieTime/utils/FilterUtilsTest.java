package pl.tworek.patryk.movieTime.utils;

import org.junit.Assert;
import org.junit.Test;
import pl.tworek.patryk.movieTime.model.Film;

import java.util.ArrayList;
import java.util.List;

public class FilterUtilsTest {

    @Test
    public void filterFilmsWithNullPattern() {
        String pattern = null;
        List<Film> films = generateTestFilmList();

        List<Film> result = FilterUtils.filterOfFilms(films, pattern);

        Assert.assertSame(films, result);
    }

    @Test
    public void filterFilmsByTitle() {
        String pattern = "thing";
        List<Film> films = generateTestFilmList();

        List<Film> expectedResult = new ArrayList<>();
        expectedResult.add(new Film(3, "The thing", "drnny", "John Carpenter", "tym", "aergag", 1.5, 5.0, 5, Film.Category.MOVIE, "afwgh"));

        List<Film> result = FilterUtils.filterOfFilms(films, pattern);

        Assert.assertEquals(expectedResult, result);

//        if(expectedResult.size() == result.size()) {
//            for(int i = 0; i < expectedResult.size(); i++) {
//                if (!expectedResult.get(i).equals(result.get(i))) {
//                    Assert.fail();
//                }
//            }
//        }
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void filterFilmsByDirector() {
        String pattern = "Carpenter";
        List<Film> films = generateTestFilmList();

        List<Film> expectedResult = new ArrayList<>();
        expectedResult.add(new Film(1, "Big trouble in little china", "producwegfwgtionYear", "John Carpenter", "tyk", "aergag", 1.5, 5.0, 5, Film.Category.MOVIE, "afwgh"));
        expectedResult.add(new Film(3, "The thing", "drnny", "John Carpenter", "tym", "aergag", 1.5, 5.0, 5, Film.Category.MOVIE, "afwgh"));

        List<Film> result = FilterUtils.filterOfFilms(films, pattern);

        Assert.assertEquals(expectedResult, result);
    }

    private List<Film> generateTestFilmList() {
        List<Film> films = new ArrayList<>();

        films.add(new Film(1, "Big trouble in little china", "producwegfwgtionYear", "John Carpenter", "tyk", "aergag", 1.5, 5.0, 5, Film.Category.MOVIE, "afwgh"));
        films.add(new Film(2, "Texas chain saw massacre", "drndrtn", "Tobe Hooper", "agatk8wg", "t8kty", 1.5, 5.0, 5, Film.Category.MOVIE, "afwgh"));
        films.add(new Film(3, "The thing", "drnny", "John Carpenter", "tym", "aergag", 1.5, 5.0, 5, Film.Category.MOVIE, "afwgh"));
        films.add(new Film(4, "Fifth Element", "producwdynydrnegfwgtionYear", "Luc Besson", "yiolu", "aergag", 1.5, 5.0, 5, Film.Category.MOVIE, "afwgh"));

        return films;
    }
}
