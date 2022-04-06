package pl.tworek.patryk.movieTime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.tworek.patryk.movieTime.model.User;
import pl.tworek.patryk.movieTime.model.view.ChangePassData;
import pl.tworek.patryk.movieTime.model.view.CreateNewUser;
import pl.tworek.patryk.movieTime.database.IFilmRepository;
import pl.tworek.patryk.movieTime.database.impl.IUserRepositoryList;
import pl.tworek.patryk.movieTime.sessionObject.SessionObject;

import javax.annotation.Resource;

@Controller
public class UserController {

    @Autowired
    IUserRepositoryList userRepository;

    @Autowired
    IFilmRepository filmRepository;

    @Resource
    SessionObject sessionObject;

    @Autowired
    ChangePassData changePassData;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("userModel", new User());
        String info = this.sessionObject.getInfo();
        if(info != null) {
            model.addAttribute("info", info);
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginForm(@ModelAttribute User user) {


        this.sessionObject.setUser(this.userRepository.authenticate(user));


        if (this.sessionObject.isLogged()) {
            return "redirect:/main";
        } else {
            this.sessionObject.setInfo("invalid credentials");
            return "redirect:/login";
        }
    }

        @RequestMapping(value = "/logout", method = RequestMethod.GET)
        public String logout() {
            this.sessionObject.setUser(null);
            return "redirect:/login";
        }

    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public String edit (Model model) {
        if (this.sessionObject.isLogged()) {
            model.addAttribute("user", this.sessionObject.getUser());
            model.addAttribute("passModel", changePassData);

            String info = this.sessionObject.getInfo();
            if (info != null) {
                model.addAttribute("info", info);
            }
            return "edit";
        } else {
            return "redirect:/login";
        }
    }


        @RequestMapping(value="/changeData", method = RequestMethod.POST)
        public String changeData(@ModelAttribute User user) {
                user.setLogin(this.sessionObject.getUser().getLogin());
                User updatedUser = this.userRepository.updateUserData(user);
                this.sessionObject.setUser(updatedUser);
                return "redirect:/edit";
        }

    @RequestMapping(value="/changePass", method = RequestMethod.POST)
    public String changePass(@ModelAttribute ChangePassData changePassData, Model model) {


        if(!changePassData.getNewPass().equals(changePassData.getRepeatedNewPass())) {
            this.sessionObject.setInfo("invalid repeated new password");
        }
        User user = new User();
        user.setPassword(changePassData.getCurrentPass());
        user.setLogin(this.sessionObject.getUser().getLogin());
        User authenticatedUser = this.userRepository.authenticate(user);

        if(authenticatedUser == null) {
            this.sessionObject.setInfo("invalid password");
        }

        user.setPassword(changePassData.getNewPass());
        User updatedUser = this.userRepository.updateUserPass(user);
        this.sessionObject.setUser(updatedUser);

        return "redirect:/edit";
    }

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String register(Model model) {

        model.addAttribute("user", new CreateNewUser());
        String info = this.sessionObject.getInfo();
        model.addAttribute("info", info);

        return "register";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String registerData(@ModelAttribute CreateNewUser createNewUser, Model model) {

        User user = new User(createNewUser.getName(), createNewUser.getSurname(),
                createNewUser.getLogin(),createNewUser.getPass(), User.Role.USER);

        boolean checkUser = userRepository.checkIfUserExists(user);

        if (checkUser) {
            this.sessionObject.setInfo("Login already exists");
            return "redirect:/login";
        } else {
            if (user.getPassword().equals(createNewUser.getRepeatedPass())) {
                userRepository.addUser(user);
                this.sessionObject.setInfo("Registration successfull");
            } else {
                this.sessionObject.setInfo("Invalid repeated password");
            }
        }



        this.sessionObject.setUser(user);


        return "redirect:/login";
    }

}
