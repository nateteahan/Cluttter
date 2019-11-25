package com.example.clutter.InterfaceMVP;

import com.example.clutter.sdk.model.RegisterUser;

public interface SignUpMVP {
    interface Model {

    }

    interface View {
        void displayButton(boolean show);
        void displayError(String message);
        void signUpSuccessful(String message);
    }

    interface Presenter {
        void enableButton(String handle, String password);
        void checkValidInfo(String email, String password);
        void signUp(RegisterUser user);
    }
}
