package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.SignUpMVP;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.SignUpActivity;
import com.example.clutter.sdk.model.Message;
import com.example.clutter.sdk.model.RegisterUser;

public class SignUpPresenter implements SignUpMVP.Presenter {
    private SignUpActivity view;

    public SignUpPresenter(SignUpActivity view) {
        this.view = view;
    }

    private class SignUpAsync extends AsyncTask<Void, Void, Message> {
        private RegisterUser user;

        public SignUpAsync(RegisterUser user) {
            this.user = user;
        }

        @Override
        protected Message doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            Message message = proxy.registerUser(user);

            return message;
        }

        @Override
        protected void onPostExecute(Message message) {
            if (message.getMessage() == null) {
                view.displayError("Please provide a profile picture.");
            }
            else if (message.getMessage().equals("Successfully created user!")) {
                view.signUpSuccessful("Welcome to Cluttter!");
            }
            else {
                view.displayError("@" + user.getUserhandle() + " is already signed up.");
            }
        }
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

    @Override
    public void signUp(RegisterUser user) {
        new SignUpAsync(user).execute();
    }

}
