package pl.tworek.patryk.movieTime.rest.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tworek.patryk.movieTime.model.Film;
import pl.tworek.patryk.movieTime.services.IFilmService;
import pl.tworek.patryk.movieTime.utils.FilterUtils;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/films")
public class FilmRestController {

    @Autowired
    IFilmService filmService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Film> getFilmById(@PathVariable int id) {
        Film film = this.filmService.getFilmById(id);
        if (film == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(film);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void addNewFilm(@RequestBody Film film) {
        this.filmService.addFilm(film);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Film> updateFilm(@RequestBody Film film) {
        if (this.filmService.getFilmById(film.getId()) == null) {
            return ResponseEntity.notFound().build();
        } else {
            this.filmService.updateFilm(film);
            return ResponseEntity.ok(film);
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteFilm(@PathVariable int id) {
        Film film = this.filmService.getFilmById(id);
        if (film == null) {
            return ResponseEntity.notFound().build();
        } else {
            this.filmService.deleteFilm(film);
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public List<Film> getFilmsByCategory(@RequestParam String pattern) {
        return FilterUtils.filterOfFilms(this.filmService.getAllFilms(), pattern);
    }
}
