package pl.tworek.patryk.movieTime.sessionObject;

import org.junit.Assert;
import org.junit.Test;
import pl.tworek.patryk.movieTime.model.User;

public class SessionObjectTest {

    @Test
    public void isLoggedUserTest() {
    SessionObject sessionObject = new SessionObject();
    User user = new User(1, "asdfkae", "awkefkaw", "kergkg", "iaweiwag", User.Role.USER);

    boolean notLoggedResult = sessionObject.isLogged();

    sessionObject.setUser(user);

    boolean loggedResult = sessionObject.isLogged();

    Assert.assertFalse(notLoggedResult);
    Assert.assertTrue(loggedResult);
    }

    @Test
    public void getInfoTest() {
        SessionObject sessionObject = new SessionObject();
        String info = "Info";
        String infoExpectedResult = "Info";

        Assert.assertNull(sessionObject.getInfo());
        sessionObject.setInfo(info);
        Assert.assertEquals(infoExpectedResult, sessionObject.getInfo());
        Assert.assertNull(sessionObject.getInfo());

    }
}
