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
    @RequestMapping(value ="/rating", method = RequestMethod.POST)
    public String rating(@RequestParam int rate,
                        Model model) {

        filmRepository.getFilmByRate(rate);

        return "redirect:/main";
    }

}
