package pl.tworek.patryk.movieTime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import pl.tworek.patryk.movieTime.model.Film;
import pl.tworek.patryk.movieTime.repository.IFilmRepository;
import pl.tworek.patryk.movieTime.repository.impl.IFilmRepositoryImpl;
import pl.tworek.patryk.movieTime.repository.impl.IUserRepositoryList;
import pl.tworek.patryk.movieTime.sessionObject.SessionObject;
import pl.tworek.patryk.movieTime.utils.FilterUtils;

import javax.annotation.Resource;
import java.util.List;

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

    @RequestMapping(value ="/changeRating", method = RequestMethod.POST)
    public String changeRating(@RequestParam int grade,
                               Model model,
                               @ModelAttribute Film film) {

        //List<Film> currentFilm = this.filmRepository.filteredFilms(filter);

        model.addAttribute("getAllFilms", this.filmRepository.getAllFilms());
        //int filmidlo = this.filmRepository.rateAfilm(ocena);
        Film updatedFilm = this.filmRepository.rateAfilm(grade);
        film.setTitle(updatedFilm.getTitle());
        film.setProductionYear(updatedFilm.getProductionYear());
        film.setDirector(updatedFilm.getDirector());
        film.setLength(updatedFilm.getLength());
        film.setGenre(updatedFilm.getGenre());
        film.setRate(updatedFilm.getRate());
        film.setRateSum(updatedFilm.getRateSum());
        film.setVoteCount(updatedFilm.getVoteCount());
        film.setCategory(updatedFilm.getCategory());

        this.filmRepository.updateFilm(film);

        //System.out.println(wynik);
        return "redirect:/main";
    }
}
