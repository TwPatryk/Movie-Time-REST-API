package pl.tworek.patryk.movieTime.database.impl;

import org.springframework.stereotype.Component;
import pl.tworek.patryk.movieTime.model.Film;
import pl.tworek.patryk.movieTime.database.IFilmRepository;

import java.util.ArrayList;
import java.util.List;


public class IFilmRepositoryImpl {

    private final List<Film> movies = new ArrayList<>();
    private final List<Film> tvShows = new ArrayList<>();
    private final List<Film> films = new ArrayList<>();


    public IFilmRepositoryImpl() {
//        this.films.add(new Film("The Thing", "1982", "John Carpenter",
//                                    "109min", "horror/sci-fi", 0, 0, 0,  Film.Category.MOVIE));
//        this.films.add(new Film("Leon", "1994", "Luc Besson",
//                                    "110min", "criminal/drama", 0, 0, 0,  Film.Category.MOVIE));
//        this.films.add(new Film("Fifth Element", "1997", "Luc Besson",
//                                     "126min", "action/sci-fi", 0, 0, 0,  Film.Category.MOVIE));
//        this.films.add(new Film("Jurrasic Park", "1993", "Steven Spielberg",
//                                   "127min", "action/adventure", 0, 0, 0,  Film.Category.MOVIE));
//        this.films.add(new Film("Squid Game", "2021", "Dong-hyuk Hwang",
//                                    "9 episodes ","drama/thriller", 0, 0, 0,  Film.Category.TVSHOW));
//
//        this.films.add(new Film("Chernobyl", "2019", "Johan Renck",
//                                    "5 episodes ","drama", 0, 0, 0,   Film.Category.TVSHOW));
//
//        this.films.add(new Film("Altered Carbon", "2018", " Miguel Sapochnik",
//                                    "18 episodes","sci-fi", 0, 0, 0,   Film.Category.TVSHOW));
//
//        this.films.add(new Film("The Killing", "2011", "Patty Jenkins",
//                                    "44 episodes","criminal/drama", 0, 0, 0,   Film.Category.TVSHOW));

    }

    public List<Film> getAllMovies() {
        List<Film> movieList = new ArrayList<>();
        for (Film currentFilm : this.films) {
            if (currentFilm.getCategory() == Film.Category.MOVIE) {
                movieList.add(currentFilm);
            }
        }
        return movieList;
    }


    public List<Film> getAllTvShows() {
        List<Film> tvShowList = new ArrayList<>();
        for (Film currentTvShow : this.films) {
            if (currentTvShow.getCategory() == Film.Category.TVSHOW) {
                tvShowList.add(currentTvShow);
            }
        }
        return tvShowList;
    }


    public List<Film> getAllFilms() {
        return this.films;
    }



    public Film rateAfilm(int grade) {
        List<Film> filmList = new ArrayList<>();
        double wynik = 0;
        for (Film filmFromDB : this.films) {
            filmFromDB.setRateSum(filmFromDB.getRateSum() +grade);
            filmFromDB.setVoteCount(filmFromDB.getVoteCount()+1);
            double number = (filmFromDB.getRateSum() / filmFromDB.getVoteCount());
            double roundedNum = round(number, 1);
            filmFromDB.setRate(roundedNum);
            return filmFromDB;
        }
        return null;
    }


    public double getRate(int grade) {
        List<Film> filmList = new ArrayList<>();
        for(Film filmFromDB : this.films) {
            System.out.println(filmFromDB);
            double ocena = filmFromDB.getRate();
            return ocena;
        }
        return 0;
    }


    public void addFilm(Film film) {
        this.films.add(new Film(film.getId(),film.getTitle(), film.getProductionYear(), film.getDirector(),
                film.getLength(),film.getGenre(), film.getRate(),film.getRateSum(), film.getVoteCount(),film.getCategory(), film.getFilePath()));
    }


    public void updateFilm(Film film) {
        for (Film filmFromDB : films) {
            if (filmFromDB.getTitle().equals(film.getTitle())) {
                filmFromDB.setRate(film.getRate());
                filmFromDB.setVoteCount(film.getVoteCount());
            }
        }
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }


}
