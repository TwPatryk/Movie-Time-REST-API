package pl.tworek.patryk.movieTime.repository;

import pl.tworek.patryk.movieTime.model.User;

public interface IUserRepository {
    User authenticate(User user);
    User updateUserData(User user);
    User updateUserPass(User user);
    void addUser(User user);
    boolean checkIfUserExists(User user);
}
