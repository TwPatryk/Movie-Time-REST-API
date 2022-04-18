package pl.tworek.patryk.movieTime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.tworek.patryk.movieTime.dao.IFilmDAO;
import pl.tworek.patryk.movieTime.model.Film;
import pl.tworek.patryk.movieTime.services.IFilmService;
import pl.tworek.patryk.movieTime.sessionObject.SessionObject;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@Controller
public class AdminController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IFilmService filmService;

    @Autowired
    IFilmDAO filmDAO;

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
    public String addFilmForm(@ModelAttribute Film film, @RequestParam MultipartFile obrazek) {
        if(!this.sessionObject.isLogged()) {
            return "redirect:/login";
        }
        try {
            String filePath = filmService.filePathGenerator();
            obrazek.transferTo(new File(filePath));
            film.setFilePath(filmService.viewPathModifier(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        IFilmService.AddFilmResult result = this.filmService.addFilm(film);
        if (result == IFilmService.AddFilmResult.FILM_ADDED) {
            this.sessionObject.setInfo("Film added successfully!");
        } else if (result == IFilmService.AddFilmResult.FILM_UPDATED) {
            this.sessionObject.setInfo("Film already exists - update successfull!");
        }

        return "redirect:/main";
    }

    @RequestMapping(value="/editFilm/{id}", method = RequestMethod.GET)
    public String editBookPage( Model model,@PathVariable int id) {

        Film film = this.filmService.getFilmById(id);
        model.addAttribute("film", film);
        model.addAttribute("user", this.sessionObject.getUser());
        model.addAttribute("info", this.sessionObject.getInfo());

        return "editFilm";
    }

    @RequestMapping(value="/editFilm/{id}", method = RequestMethod.POST)
    public String editBook(@ModelAttribute Film film, @PathVariable int id) {
        film.setId(id);
        this.filmDAO.updateFilm(film);

        return "redirect:/main";
    }

    @RequestMapping(value="/deleteFilm/{id}", method = RequestMethod.GET)
    public String deleteFilmForm(@PathVariable int id, Model model) {

        Film film = this.filmDAO.getFilmById(id);
        model.addAttribute("film", film);
        model.addAttribute("user", this.sessionObject.getUser());
        model.addAttribute("info", this.sessionObject.getInfo());

        this.filmDAO.deleteFilm(id);
        return "redirect:/main";
    }
}
