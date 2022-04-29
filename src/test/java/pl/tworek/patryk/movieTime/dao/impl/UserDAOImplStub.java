package pl.tworek.patryk.movieTime.dao.impl;

import pl.tworek.patryk.movieTime.dao.IUserDAO;
import pl.tworek.patryk.movieTime.model.User;

import java.util.List;

public class UserDAOImplStub implements IUserDAO {
    @Override
    public User getUserByLogin(String login) {
        if (login.equals("admin")) {
            User user = new User();

            user.setId(1);
            user.setLogin("admin");
            user.setName("Pan");
            user.setSurname("Admin");
            user.setPassword("admin");
            user.setRole(User.Role.ADMIN);

            return user;
        } else {
            return null;
        }

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void persistUser(User user) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

}
