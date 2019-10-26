package com.example.clutter.InterfaceMVP;

public interface CreateAccountMVP {
    interface Model {

    }

    interface View {
        void displayButton(boolean show);
    }

    interface Presenter {
        void createAccount();
        void checkUserInfo(String firstName, String lastName, String emailAddress);
    }
}
