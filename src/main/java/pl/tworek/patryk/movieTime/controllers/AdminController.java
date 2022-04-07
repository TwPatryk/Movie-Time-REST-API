package pl.tworek.patryk.movieTime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.tworek.patryk.movieTime.model.Film;
import pl.tworek.patryk.movieTime.database.IFilmRepository;
import pl.tworek.patryk.movieTime.sessionObject.SessionObject;

import javax.annotation.Resource;

@Controller
public class AdminController {

    @Autowired
    IFilmRepository filmRepository;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(value="/addFilm", method= RequestMethod.GET)
    public String addFilmForm(Model model) {
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }

        model.addAttribute("film", new Film());
        model.addAttribute("user", this.sessionObject.getUser());
        model.addAttribute("info", this.sessionObject.getInfo());
        return "addFilm";
    }

    @RequestMapping(value="/addFilm", method= RequestMethod.POST)
    public String addFilmForm(@ModelAttribute Film film) {
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }


        if (film.getTitle().equals("") || film.getProductionYear().equals("") || film.getDirector().equals("") ||
                film.getLength().equals("") || film.getGenre().equals("")) {
            this.sessionObject.setInfo("Please, fill the whole form");
            return "redirect:/addFilm";
        } else {
            Film addedFilm = new Film(film.getId(),film.getTitle(),film.getProductionYear(),film.getDirector(),
                    film.getLength(),film.getGenre(),film.getRate(), film.getRateSum(),film.getVoteCount(),film.getCategory());
            this.filmRepository.addFilm(addedFilm);
            this.sessionObject.setInfo("Film added successfully!");
        }
        return "redirect:/main";
    }

    @RequestMapping(value="/editFilm/{id}", method = RequestMethod.GET)
    public String editBookPage( Model model,@PathVariable int id) {

        Film film = this.filmRepository.getFilmById(id);
        model.addAttribute("film", film);
        model.addAttribute("user", this.sessionObject.getUser());
        model.addAttribute("info", this.sessionObject.getInfo());

        return "editFilm";
    }

    @RequestMapping(value="/editFilm/{id}", method = RequestMethod.POST)
    public String editBook(@ModelAttribute Film film, @PathVariable int id) {
        film.setId(id);
        this.filmRepository.updateFilm(film);

        return "redirect:/main";
    }

    @RequestMapping(value="/deleteFilm/{id}", method = RequestMethod.GET)
    public String deleteFilmForm(@PathVariable int id, Model model) {

        Film film = this.filmRepository.getFilmById(id);
        System.out.println(film.getTitle());
        model.addAttribute("film", film);
        model.addAttribute("user", this.sessionObject.getUser());
        model.addAttribute("info", this.sessionObject.getInfo());

        this.filmRepository.deleteFilm(id);
        return "redirect:/main";
    }
}
