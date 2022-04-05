package pl.tworek.patryk.movieTime.model.view;

import org.springframework.stereotype.Component;
import pl.tworek.patryk.movieTime.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChangePassData {
    private String currentPass;
    private String newPass;
    private String repeatedNewPass;


    public ChangePassData() {
    }

    public ChangePassData(String currentPass, String newPass, String repeatedNewPass) {
        this.currentPass = currentPass;
        this.newPass = newPass;
        this.repeatedNewPass = repeatedNewPass;
    }

    public String getCurrentPass() {
        return currentPass;
    }

    public void setCurrentPass(String currentPass) {
        this.currentPass = currentPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getRepeatedNewPass() {
        return repeatedNewPass;
    }

    public void setRepeatedNewPass(String repeatedNewPass) {
        this.repeatedNewPass = repeatedNewPass;
    }
}
