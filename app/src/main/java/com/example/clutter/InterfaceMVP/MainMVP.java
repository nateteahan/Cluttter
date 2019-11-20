package com.example.clutter.InterfaceMVP;

public interface MainMVP {

    interface Model {

    }

    interface View {
        void displayButton(boolean setClickable);
        void displayError(String message);
        void successfulLogin();
    }

    interface Presenter {
        void enableLogin(String handle, String password);
        void signInButtonClicked(String handle, String password);
//        void updateUserHandle(String handle);
//        void updateUserPassword(String password);
    }
}
