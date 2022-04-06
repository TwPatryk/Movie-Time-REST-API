package pl.tworek.patryk.movieTime.database.impl;

import org.springframework.stereotype.Component;
import pl.tworek.patryk.movieTime.model.User;
import pl.tworek.patryk.movieTime.database.IUserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class IUserRepositoryList implements IUserRepository {


    private final List<User> users = new ArrayList<>();

    public IUserRepositoryList() {
        this.users.add(new User("Patryk", "Tworek","admin",
                "admin", User.Role.ADMIN));
        this.users.add(new User("Jan", "Kowalski", "jan",
                "jan", User.Role.USER));
    }

    @Override
    public User authenticate(User user) {
        for (User currentUser : this.users) {
            if (user.getLogin().equals(currentUser.getLogin())) {
                if (user.getPassword().equals(currentUser.getPassword())) {
                    return currentUser;
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public User updateUserData(User user) {
        for (User userFromDataBase : this.users) {
            if (userFromDataBase.getLogin().equals(user.getLogin())) {
                userFromDataBase.setName(user.getName());
                userFromDataBase.setSurname(user.getSurname());
                return userFromDataBase;
            }
        }
        return null;
    }

    @Override
    public User updateUserPass(User user) {
        for (User userFromDB : this.users) {
            if (userFromDB.getLogin().equals(user.getLogin())) {
                userFromDB.setPassword(user.getPassword());
                return userFromDB;
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        this.users.add(new User(user.getName(),user.getSurname(),user.getLogin(),user.getPassword(),user.getRole()));
    }

    @Override
    public boolean checkIfUserExists(User user) {
        for (User userfromDB : this.users) {
            if (userfromDB.getLogin().equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }


}
