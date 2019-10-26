package com.example.clutter.Presenter;

import com.example.clutter.InterfaceMVP.MainMVP;
import com.example.clutter.View.SignInActivity;

import com.example.clutter.Model.User;

public class SignInPresenter implements MainMVP.Presenter {
    private SignInActivity view;
    private User user;

    // Constructor
    public SignInPresenter(SignInActivity mainView) {
        this.user = new User();
        this.view = mainView;
    }

    @Override
    public void signInButtonClicked(String handle, String password) {
        if (handle.isEmpty()) {
            view.displayError("Username field left empty. Try again.");
        }
        else if (password.length() <= 3) {
            view.displayError("Password must be at least 4 characters long. Try again");
        }
        else {
            view.successfulLogin();
        }
    }

    @Override
    public void updateUserHandle(String handle) {
        user.setUserHandle(handle);
    }

    @Override
    public void updateUserPassword(String password) {
        user.setUserPassword(password);
    }

    @Override
    public void enableLogin(String handle, String password) {
        if (handle.isEmpty() || password.isEmpty()) {
            view.displayButton(false);
        }
        else {
            view.displayButton(true);
        }
    }
}
