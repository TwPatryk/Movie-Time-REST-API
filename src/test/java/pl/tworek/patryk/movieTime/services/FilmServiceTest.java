package pl.tworek.patryk.movieTime.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.tworek.patryk.movieTime.configuration.TestConfiguration;
import pl.tworek.patryk.movieTime.dao.IFilmDAO;
import pl.tworek.patryk.movieTime.model.Film;
import pl.tworek.patryk.movieTime.sessionObject.SessionObject;

import javax.annotation.Resource;
import java.io.File;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfiguration.class})
public class FilmServiceTest {

    @Resource
    SessionObject sessionObject;

    @MockBean
    IFilmDAO filmDAO;

    @Autowired
    IFilmService filmService;

    @MockBean
    IUserService userService;

    @Test
    public void addNewFilmTest() {
        Film film = new Film(1, "The Thing", "1982", "John Carpenter", "124min", "sci-fi/horror", 1.0, 1.0, 1, Film.Category.MOVIE, "c:\\kasdfk");
        Mockito.when(this.filmDAO.getFilmByTitle(Mockito.any())).thenReturn(null);

        IFilmService.AddFilmResult  result = this.filmService.addFilm(film);

        Mockito.verify(this.filmDAO, Mockito.times(1)).persistFilm(film);
        Assert.assertEquals(IFilmService.AddFilmResult.FILM_ADDED, result);

    }
    @Test
    public void addExistingFilmTest() {
        Film film = new Film(1, "The Thing", "1982", "John Carpenter", "124min", "sci-fi/horror", 1.0, 1.0, 1, Film.Category.MOVIE, "c:\\kasdfk");
        Mockito.when(this.filmDAO.getFilmByTitle(Mockito.any())).thenReturn(new Film(1, "The Thing", "1982", "John Carpenter", "124min", "sci-fi/horror", 1.0, 1.0, 1, Film.Category.MOVIE, "c:\\kasdfk"));

        IFilmService.AddFilmResult result = this.filmService.addFilm(film);
        Mockito.verify(this.filmDAO, Mockito.times(1)).updateFilm(Mockito.any());
        Assert.assertEquals(IFilmService.AddFilmResult.FILM_UPDATED, result);
    }

    @Test
    public void getMoviesWithFilterTest() {
        String category = "movies";

        this.filmService.getFilmsByCategoryWithFilter(category);

        Mockito.verify(this.filmDAO, Mockito.times(1)).getFilmsByCategory(Film.Category.MOVIE);
    }

    @Test
    public void getTvShowsWithFilterTest() {
        String category = "tvShows";

        this.filmService.getFilmsByCategoryWithFilter(category);

        Mockito.verify(this.filmDAO, Mockito.times(1)).getFilmsByCategory(Film.Category.TVSHOW);
    }

    @Test
    public void getFilmsWithoutCategoryTest() {
        String category = "lallgwalggk";

        this.filmService.getFilmsByCategoryWithFilter(category);

        Mockito.verify(this.filmDAO, Mockito.times(1)).getAllFilms();
    }

    @Test
    public void getFilmsWithNullCategory() {
        String category = null;

        this.filmService.getFilmsByCategoryWithFilter(category);

        Mockito.verify(this.filmDAO, Mockito.times(1)).getAllFilms();
    }

    @Test
    public void filePathGeneratorTest() {
        Random random = new Random();
        int number = random.nextInt(100);
        String imgDestFullPath = "lawegklawgalkrgakjlawg" + number;
        File filePath = new File(imgDestFullPath);
        filePath.mkdir();
        String result = imgDestFullPath + "\\obrazek.jpg" ;

        String expectedResult = "lawegklawgalkrgakjlawg" + number + "\\obrazek.jpg";

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void viewPathModifierTest() {
        String filePath = "alwejfawle\\lalwekf\\static\\alweflwaegl\\";
        String[] stringSplit = filePath.split("static");
        String f = stringSplit[1];
        String result = f.replace("\\", "/");

        String expectedResult = "/alweflwaegl/";

        Assert.assertEquals(expectedResult, result);
    }
}
