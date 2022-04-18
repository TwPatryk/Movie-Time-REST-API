package pl.tworek.patryk.movieTime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.tworek.patryk.movieTime.dao.IFilmDAO;
import pl.tworek.patryk.movieTime.model.Film;
import pl.tworek.patryk.movieTime.database.IFilmRepository;
import pl.tworek.patryk.movieTime.database.impl.IUserRepositoryList;
import pl.tworek.patryk.movieTime.services.IFilmService;
import pl.tworek.patryk.movieTime.sessionObject.SessionObject;
import pl.tworek.patryk.movieTime.utils.FilterUtils;

import javax.annotation.Resource;

@Controller
public class CommonController {

    @Autowired
    IFilmRepository filmRepository;

    @Resource
    SessionObject sessionObject;

    @Autowired
    IFilmService filmService;

    @Autowired
    IFilmDAO filmDAO;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String commonRedirect() {
        return "redirect:/main";
    }


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String runMainPage(Model model, @RequestParam(defaultValue = "none") String category) {

        if (sessionObject.isLogged()) {
            model.addAttribute("getAllFilms", this.filmService.getFilmsByCategoryWithFilter(category));
            model.addAttribute("user", this.sessionObject.getUser());
            model.addAttribute("filter", this.sessionObject.getFilter());
            return "main";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filterFilm(@RequestParam String filter) {
        if (sessionObject.isLogged()) {
            this.sessionObject.setFilter(filter);
            return "redirect:/main";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value="/rateFilm/{id}", method = RequestMethod.GET)
    public String editBookPage( Model model,@PathVariable int id) {


        model.addAttribute("film", filmRepository.getFilmById(id));
        model.addAttribute("user", this.sessionObject.getUser());
        return "rateFilm";
    }


    @RequestMapping(value="/rateFilm/{id}", method = RequestMethod.POST)
    public String editBook(@ModelAttribute Film film, @PathVariable int id, @RequestParam double grade) {
        //film.setId(id);
        //this.filmRepository.updateFilm(film);
        Film updatedFilm = this.filmRepository.getFilmById(film.getId());




        updatedFilm.setRateSum(updatedFilm.getRateSum() + grade);
        updatedFilm.setVoteCount(updatedFilm.getVoteCount() + 1);
        double number = (updatedFilm.getRateSum() / updatedFilm.getVoteCount());
        double roundedNum = round(number, 1);
        updatedFilm.setRate(roundedNum);

        System.out.println("updated rate sum" + updatedFilm.getRateSum());

        this.filmRepository.updateFilm(updatedFilm);

//        public Film rateAfilm(int grade) {
//            List<Film> filmList = new ArrayList<>();
//            double wynik = 0;
//            for (Film filmFromDB : this.films) {
//                filmFromDB.setRateSum(filmFromDB.getRateSum() +grade);
//                filmFromDB.setVoteCount(filmFromDB.getVoteCount()+1);
//                double number = (filmFromDB.getRateSum() / filmFromDB.getVoteCount());
//                double roundedNum = round(number, 1);
//                filmFromDB.setRate(roundedNum);
//                return filmFromDB;
//            }
//            return null;
//        }

        return "redirect:/main";
    }
    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
