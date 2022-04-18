package pl.tworek.patryk.movieTime.dao;

import pl.tworek.patryk.movieTime.model.User;

public interface IUserDAO {
    User getUserByLogin(String login);
    void updateUser(User user);
    void persistUser(User user);
}
