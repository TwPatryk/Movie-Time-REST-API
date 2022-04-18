package pl.tworek.patryk.movieTime.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tworek.patryk.movieTime.dao.IUserDAO;
import pl.tworek.patryk.movieTime.model.User;
import pl.tworek.patryk.movieTime.model.view.ChangePassData;
import pl.tworek.patryk.movieTime.model.view.CreateNewUser;
import pl.tworek.patryk.movieTime.services.IUserService;
import pl.tworek.patryk.movieTime.sessionObject.SessionObject;

import javax.annotation.Resource;

@Service
public class IUserServiceImpl implements IUserService {

    @Autowired
    IUserDAO userDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public User authenticate(User user) {
        User userFromDatabase = this.userDAO.getUserByLogin(user.getLogin());
        if(userFromDatabase != null && userFromDatabase.getPassword().equals(user.getPassword())) {
            return userFromDatabase;
        } else {
            return null;
        }
    }

    @Override
    public User updateUserData(User user) {
        User currentUser = this.sessionObject.getUser();
        currentUser.setName(user.getName());
        currentUser.setSurname(user.getSurname());
        this.userDAO.updateUser(currentUser);
        return currentUser;
    }

    @Override
    public User updateUserPass(ChangePassData changePassData) {
        User user = new User();
        user.setLogin(this.sessionObject.getUser().getLogin());
        user.setPassword(changePassData.getCurrentPass());
        User authenticatedUser = this.authenticate(user);
        if (authenticatedUser == null) {
            return null;
        }
        authenticatedUser.setPassword(changePassData.getNewPass());

        this.userDAO.updateUser(authenticatedUser);

        return authenticatedUser;
    }

    @Override
    public boolean registerUser(CreateNewUser createNewUser) {
        User userFromDatabase = this.userDAO.getUserByLogin(createNewUser.getLogin());
        if(userFromDatabase != null) {
            return false;
        }
        User user = new User();
        user.setName(createNewUser.getName());
        user.setSurname(createNewUser.getSurname());
        user.setLogin(createNewUser.getLogin());
        user.setPassword(createNewUser.getPass());
        user.setRole(User.Role.USER);

        this.userDAO.persistUser(user);
        return true;
    }
}
