package pl.tworek.patryk.movieTime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.tworek.patryk.movieTime.model.Film;
import pl.tworek.patryk.movieTime.database.IFilmRepository;
import pl.tworek.patryk.movieTime.database.impl.IUserRepositoryList;
import pl.tworek.patryk.movieTime.sessionObject.SessionObject;
import pl.tworek.patryk.movieTime.utils.FilterUtils;

import javax.annotation.Resource;

@Controller
public class CommonController {

    @Autowired
    IFilmRepository filmRepository;

    @Autowired
    IUserRepositoryList userRepositoryList;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String commonRedirect(Model model) {
        if (sessionObject.isLogged()) {
            model.addAttribute("getAllFilms", filmRepository.getAllFilms());
            model.addAttribute("user", this.sessionObject.getUser());
            return "main";
        } else {
            return "redirect:/login";
        }
    }


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String runMainPage(Model model, @RequestParam(defaultValue = "none") String category) {

        if (sessionObject.isLogged()) {
            model.addAttribute("getAllFilms", filmRepository.getAllFilms());
            model.addAttribute("user", this.sessionObject.getUser());

            switch(category) {
                case "movies":
                    model.addAttribute("getAllFilms",
                            FilterUtils.filterOfFilms(this.filmRepository.getAllMovies(),
                                    this.sessionObject.getFilter()));
                    break;
                case"tvShows":
                    model.addAttribute("getAllFilms",
                            FilterUtils.filterOfFilms(this.filmRepository.getAllTvShows(),
                                    this.sessionObject.getFilter()));
                    break;
                default:
                    model.addAttribute("getAllFilms",
                                FilterUtils.filterOfFilms(this.filmRepository.getAllFilms(),
                                        this.sessionObject.getFilter()));

                    break;
            }
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
//    @RequestMapping(value ="/rating", method = RequestMethod.POST)
//    public String rating(@RequestParam int rate,
//                        Model model) {
//        if(sessionObject.isLogged()) {
//            List<Film> filmList = filmRepository.getAllFilms();
//            model.addAttribute("rating", filmList.get(rate));
//            System.out.println(rate);
//            return "main";
//        } else {
//            return "redirect:/login";
//        }
//    }

//    @RequestMapping(value ="/changeRating", method = RequestMethod.GET)
//    public String changeRatingForm(@PathVariable int grade, Model model) {
//
//        System.out.println(grade);
//        //Film film = this.filmRepository.getFilmById(id);
//        //System.out.println(film.getTitle());
//        //model.addAttribute("film", film);
//        model.addAttribute("user", this.sessionObject.getUser());
//        model.addAttribute("info", this.sessionObject.getInfo());
//
//        return "changeRating";
//    }
//
//
//    @RequestMapping(value="/changeRating", method = RequestMethod.POST)
//    public String deleteFilmForm(@PathVariable int grade, Model model) {
//
//        System.out.println(grade);
//        //Film film = this.filmRepository.getFilmById(id);
//        //System.out.println(film.getTitle());
//        //model.addAttribute("film", film);
//        model.addAttribute("user", this.sessionObject.getUser());
//        model.addAttribute("info", this.sessionObject.getInfo());
//
//        return "redirect:/main";
//    }

//    @RequestMapping(value ="/changeRating", method = RequestMethod.POST)
//    public String changeRating(@RequestParam int grade,
//                               Model model,
//                               @ModelAttribute Film film) {



//        @RequestMapping(value="/changeRating", method = RequestMethod.GET)
//        public String changeRatingForm(@RequestParam int rate, Model model) {
//
//            System.out.println(rate);
//
//            Film film = this.filmRepository.getFilmById(id);
//            System.out.println(film.getTitle());
//            model.addAttribute("film", film);
//            model.addAttribute("user", this.sessionObject.getUser());
//            model.addAttribute("info", this.sessionObject.getInfo());
//
//            this.filmRepository.deleteFilm(id);
//            return "/changeRating";
//        }



//        model.addAttribute("getAllFilms", this.filmRepository.getAllFilms());
//        int filmidlo = this.filmRepository.rateAfilm(ocena);
//        Film updatedFilm = this.filmRepository.rateAfilm(grade);
//        film.setTitle(updatedFilm.getTitle());
//        film.setProductionYear(updatedFilm.getProductionYear());
//        film.setDirector(updatedFilm.getDirector());
//        film.setLength(updatedFilm.getLength());
//        film.setGenre(updatedFilm.getGenre());
//        film.setRate(updatedFilm.getRate());
//        film.setRateSum(updatedFilm.getRateSum());
//        film.setVoteCount(updatedFilm.getVoteCount());
//        film.setCategory(updatedFilm.getCategory());
//
//        this.filmRepository.updateFilm(film);

        //System.out.println(wynik);
        //return "redirect:/main";
   // }

    @RequestMapping(value="/changeRating/{id}", method = RequestMethod.GET)
    public String changeRatingForm( Model model,@PathVariable int id) {

        Film film = this.filmRepository.getFilmById(id);
        this.filmRepository.updateFilm(film);
        model.addAttribute("film", film);
        model.addAttribute("user", this.sessionObject.getUser());
        model.addAttribute("info", this.sessionObject.getInfo());

        return "changeRating";
    }

    @RequestMapping(value="/changeRating/{id}", method = RequestMethod.POST)
    public String changeRating(@ModelAttribute Film film, @PathVariable int id, @RequestParam double rate) {

        film.setRate(rate);
//        film.setId(id);
        this.filmRepository.updateFilm(film);
        System.out.println(film.getTitle());
        System.out.println(film.getRate());

        return "redirect:/main";
    }
}
