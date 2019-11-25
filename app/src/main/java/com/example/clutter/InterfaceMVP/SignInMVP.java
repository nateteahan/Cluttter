package com.example.clutter.InterfaceMVP;

import com.example.clutter.sdk.model.SignInUser;

public interface SignInMVP {

    interface Model {

    }

    interface View {
        void displayButton(boolean setClickable);
        void displayError(String message);
        void successfulLogin();
        void signIn(SignInUser user);
    }

    interface Presenter {
        void enableLogin(String handle, String password);
        void signInButtonClicked(String handle, String password);
        void signIn(SignInUser user);
    }
}
