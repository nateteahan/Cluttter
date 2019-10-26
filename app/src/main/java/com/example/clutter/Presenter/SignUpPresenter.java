package com.example.clutter.Presenter;

import com.example.clutter.InterfaceMVP.SignUpMVP;
import com.example.clutter.View.SignUpActivity;

public class SignUpPresenter implements SignUpMVP.Presenter {
    private SignUpActivity view;

    public SignUpPresenter(SignUpActivity view) {
        this.view = view;
    }

    @Override
    public void enableButton(String handle, String password) {


        if (handle.isEmpty() || password.isEmpty()) {
            view.displayButton(false);
        }
        else {
            view.displayButton(true);
        }
        return;
    }

    @Override
    public void checkValidInfo(String email, String password) {

    }
}
