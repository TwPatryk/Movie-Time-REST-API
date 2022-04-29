package pl.tworek.patryk.movieTime.rest.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tworek.patryk.movieTime.model.User;
import pl.tworek.patryk.movieTime.services.IUserService;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = this.userService.getUserById(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public User addUser(@RequestBody User user) {
        this.userService.persistUser(user);
        return user;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if(this.userService.getUserById(user.getId()) == null) {
            return ResponseEntity.notFound().build();
        } else {
            this.userService.updateUser(user);
            return ResponseEntity.ok(user);
        }

    }
}
