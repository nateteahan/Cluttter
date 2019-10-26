package com.example.clutter.Presenter;

import com.example.clutter.InterfaceMVP.CreateAccountMVP;
import com.example.clutter.View.CreateAccountActivity;

public class CreateAccountPresenter implements CreateAccountMVP.Presenter {
    private CreateAccountActivity view;

    public CreateAccountPresenter(CreateAccountActivity createAccountView) {
        this.view = createAccountView;
    }

    @Override
    public void createAccount() {

    }

    @Override
    public void checkUserInfo(String firstName, String lastName, String email) {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            view.displayButton(false);;
        }
        else if (email.isEmpty()) {
            view.displayButton(false);;
        }
        else {
            boolean validEmail = false;
            //Check for '@' symbol to determine valid email address
            for (int i = 0; i < email.length() ; i++) {
                if (email.charAt(i) == '@') {
                    validEmail = true;
                }
            }

            if (!validEmail) {
                view.displayButton(false);;
            }
            else {
                view.displayButton(true);
            }
        }
    }
}
