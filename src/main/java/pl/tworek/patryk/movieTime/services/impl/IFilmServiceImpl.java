package pl.tworek.patryk.movieTime.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tworek.patryk.movieTime.dao.IFilmDAO;
import pl.tworek.patryk.movieTime.model.Film;
import pl.tworek.patryk.movieTime.services.IFilmService;
import pl.tworek.patryk.movieTime.sessionObject.SessionObject;
import pl.tworek.patryk.movieTime.utils.FilterUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class IFilmServiceImpl implements IFilmService {

    @Autowired
    IFilmDAO filmDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public AddFilmResult addFilm(Film film) {
        Film filmFromDB = this.filmDAO.getFilmByTitle(film.getTitle());
        if (filmFromDB == null) {
            this.filmDAO.persistFilm(film);
            return AddFilmResult.FILM_ADDED;
        } else {
            film.setId(filmFromDB.getId());
            this.filmDAO.updateFilm(film);
            return AddFilmResult.FILM_UPDATED;
        }
    }
    public String filePathGenerator() {

        Random random = new Random();
        int number = random.nextInt(100);
        String imgDestFullPath = "C:\\Users\\Riggs\\Downloads\\it camp\\Moje projekty\\Movie time & Bookstore & NBP\\movie-time z gita aktual\\src\\main\\resources\\static\\pliki\\" + number;
        //String imgDest = "\\pliki\\" + number;
        File filePath = new File(imgDestFullPath);
        filePath.mkdir();

        String imgDestination = imgDestFullPath + "\\obrazek.jpg" ;
        return imgDestination;
    }

    @Override
    public String viewPathModifier(String filePath) {
        String[] stringSplit = filePath.split("static");
        String f = stringSplit[1];
        String g = f.replace("\\", "/");

        return g;
    }

    @Override
    public Film getFilmById(int id) {
        return this.filmDAO.getFilmById(id);
    }

    @Override
    public List<Film> getFilmsByCategoryWithFilter(String category) {

        if(category == null) {
            category = "";
        }

        switch(category) {
            case "movies":
                return FilterUtils.filterOfFilms(this.filmDAO.getFilmsByCategory(Film.Category.MOVIE),
                                this.sessionObject.getFilter());
            case"tvShows":
                return FilterUtils.filterOfFilms(this.filmDAO.getFilmsByCategory(Film.Category.TVSHOW),
                        this.sessionObject.getFilter());
            default:
                return FilterUtils.filterOfFilms(this.filmDAO.getAllFilms(),
                        this.sessionObject.getFilter());
        }
    }

    @Override
    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        films = this.filmDAO.getAllFilms();
        return films;
    }

    @Override
    public void updateFilm(Film film) {
        this.filmDAO.updateFilm(film);
    }

    @Override
    public void deleteFilm(Film film) {
        this.filmDAO.deleteFilm(film);
    }

}
