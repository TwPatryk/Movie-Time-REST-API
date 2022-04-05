package pl.tworek.patryk.movieTime.sessionObject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.tworek.patryk.movieTime.model.User;
import pl.tworek.patryk.movieTime.repository.impl.IUserRepositoryList;

@SessionScope
@Component
public class SessionObject {

    private User user= null;
    private String info = null;
    private String filter = null;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLogged() {
        return !(this.user == null);
        }

    public String getInfo() {
        String result = this.info;
        this.info = null;
        return result;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}

