package pl.tworek.patryk.movieTime.services;

import pl.tworek.patryk.movieTime.model.User;
import pl.tworek.patryk.movieTime.model.view.ChangePassData;
import pl.tworek.patryk.movieTime.model.view.CreateNewUser;

import java.util.List;

public interface IUserService {

    User authenticate(User user);
    User updateUserData(User user);
    User updateUserPass(ChangePassData changePassData);
    boolean registerUser(CreateNewUser createNewUser);
    List<User> getAllUsers();
    User getUserById(int id);
    void persistUser(User user);
    void updateUser(User user);
}
