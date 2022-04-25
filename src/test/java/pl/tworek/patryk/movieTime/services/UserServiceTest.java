package pl.tworek.patryk.movieTime.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.tworek.patryk.movieTime.configuration.TestConfiguration;
import pl.tworek.patryk.movieTime.dao.IFilmDAO;
import pl.tworek.patryk.movieTime.dao.IUserDAO;
import pl.tworek.patryk.movieTime.model.User;
import pl.tworek.patryk.movieTime.model.view.ChangePassData;
import pl.tworek.patryk.movieTime.model.view.CreateNewUser;
import pl.tworek.patryk.movieTime.sessionObject.SessionObject;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfiguration.class})
public class UserServiceTest {

    @Autowired
    IUserService userService;

    @MockBean
    IUserDAO userDAO;

    @MockBean
    IFilmDAO filmDAO;

    @Resource
    SessionObject sessionObject;

    @Before
    public void setUpMocks() {
        Mockito.when(this.userDAO.getUserByLogin(Mockito.anyString())).thenReturn(null);
        Mockito.when(this.userDAO.getUserByLogin("admin")).thenReturn(generateFakeAdmin());
    }


    @Test
    public void authenticateCorrectLoginAndPassTest() {

        User user = new User();
        user.setLogin("admin");
        user.setPassword("admin");

        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setLogin("admin");
        expectedUser.setName("Pan");
        expectedUser.setSurname("Admin");
        expectedUser.setPassword("admin");
        expectedUser.setRole(User.Role.ADMIN);


        User resultUser = this.userService.authenticate(expectedUser);

        Assert.assertEquals(expectedUser, resultUser);
    }

    @Test
    public void authenticateIncorrectLoginTest() {

        User user = new User();
        user.setLogin("alselfld");
        user.setPassword("alglawgrl");

        User resultUser = this.userService.authenticate(user);

        Assert.assertNull(resultUser);
    }

    @Test
    public void authenticateIncorrectPassword() {

        User user = new User();
        user.setLogin("admin");
        user.setPassword("alglawgrl");

        User resultUser = this.userService.authenticate(user);

        Assert.assertNull(resultUser);
    }

    @Test
    public void registerUsedLoginUser() {

        CreateNewUser createNewUser = new CreateNewUser();
        createNewUser.setLogin("admin");
        createNewUser.setPass("alwfawlg");
        createNewUser.setRepeatedPass("alwfawlg");
        createNewUser.setName("aflewglw");
        createNewUser.setSurname("awekgk");

        boolean registrationResult = this.userService.registerUser(createNewUser);

        Assert.assertFalse(registrationResult);

        Mockito.verify(this.userDAO, Mockito.never()).persistUser(Mockito.any());
    }

    @Test
    public void registerNewUser() {

        CreateNewUser createNewUser = new CreateNewUser();
        createNewUser.setLogin("test");
        createNewUser.setPass("test");
        createNewUser.setRepeatedPass("test");
        createNewUser.setName("Testowy");
        createNewUser.setSurname("Testowy");

        boolean registrationResult = this.userService.registerUser(createNewUser);

        Assert.assertTrue(registrationResult);

        Mockito.verify(this.userDAO, Mockito.times(1)).persistUser(Mockito.any());
    }
    private User generateFakeAdmin() {
        User user = new User();
        user.setId(1);
        user.setLogin("admin");
        user.setName("Pan");
        user.setSurname("Admin");
        user.setPassword("admin");
        user.setRole(User.Role.ADMIN);

        return user;
    }

    @Test
    public void updateUserDataTest() {

        User user = new User();
        user.setName("Mateusz");
        user.setSurname("Bereda");
        User currentUser = generateFakeAdmin();
        this.sessionObject.setUser(currentUser);

        User updateResult = this.userService.updateUserData(user);

        Assert.assertEquals(user.getName(), updateResult.getName());
        Assert.assertEquals(user.getSurname(), updateResult.getSurname());
        Assert.assertEquals(currentUser.getId(), updateResult.getId());
        Assert.assertEquals(currentUser.getLogin(), updateResult.getLogin());
        Assert.assertEquals(currentUser.getPassword(), updateResult.getPassword());
        Assert.assertEquals(currentUser.getRole(), updateResult.getRole());

        Mockito.verify(this.userDAO, Mockito.times(1)).updateUser(Mockito.any());
    }

    @Test
    public void updateUserPassWithCorrectAuthentication() {
        ChangePassData changePassData = new ChangePassData();
        changePassData.setCurrentPass("admin");
        changePassData.setNewPass("admin2");
        changePassData.setRepeatedNewPass("admin2");
        User currentUser = generateFakeAdmin();
        this.sessionObject.setUser(currentUser);

        User updateResult = this.userService.updateUserPass(changePassData);

        Assert.assertEquals(currentUser.getId(), updateResult.getId());
        Assert.assertEquals(currentUser.getName(), updateResult.getName());
        Assert.assertEquals(currentUser.getSurname(), updateResult.getSurname());
        Assert.assertEquals(currentUser.getLogin(), updateResult.getLogin());
        Assert.assertEquals(changePassData.getNewPass(), updateResult.getPassword());
        Assert.assertEquals(currentUser.getRole(), updateResult.getRole());

        Mockito.verify(this.userDAO, Mockito.times(1)).updateUser(Mockito.any());

    }

    @Test
    public void updateUserPassWithIncorrectAuthentication() {
        ChangePassData changePassData = new ChangePassData();
        changePassData.setCurrentPass("incorrectPassword");
        changePassData.setNewPass("admin2");
        changePassData.setRepeatedNewPass("admin2");
        User currentUser = generateFakeAdmin();
        this.sessionObject.setUser(currentUser);

        User updateResult = this.userService.updateUserPass(changePassData);

        Assert.assertNull(updateResult);
        Mockito.verify(this.userDAO, Mockito.never()).updateUser(Mockito.any());
    }
}
