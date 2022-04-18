package pl.tworek.patryk.movieTime.services;

import pl.tworek.patryk.movieTime.model.User;
import pl.tworek.patryk.movieTime.model.view.ChangePassData;
import pl.tworek.patryk.movieTime.model.view.CreateNewUser;

public interface IUserService {

    User authenticate(User user);
    User updateUserData(User user);
    User updateUserPass(ChangePassData changePassData);
    boolean registerUser(CreateNewUser createNewUser);
}
