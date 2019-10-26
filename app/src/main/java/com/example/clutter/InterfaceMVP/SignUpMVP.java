package com.example.clutter.InterfaceMVP;

public interface SignUpMVP {
    interface Model {

    }

    interface View {
        void displayButton(boolean show);
        void displayError(String message);
    }

    interface Presenter {
        void enableButton(String handle, String password);
        void checkValidInfo(String email, String password);
    }
}
