package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.MainMVP;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.SignInActivity;
import com.example.clutter.sdk.model.User;

public class SignInPresenter implements MainMVP.Presenter {
    private SignInActivity view;
    protected String userHandle;

    private class VerifyUserAsync extends AsyncTask<Void, Void, Boolean> {
        private VerifyUserAsync(String handle) {
            userHandle = handle;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();

            // Bug fix for the URL encoding of the "@" symbol
            userHandle = userHandle.replace("@", "");
            User user = proxy.getUser(userHandle);

            if (user.getUserHandle() != null) {
                return true;
            }
            else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                view.successfulLogin();
            }
            else {
                view.displayError("This userHandle does not exist.");
            }
        }
    }

    // Constructor
    public SignInPresenter(SignInActivity mainView) {
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
    public void enableLogin(String handle, String password) {
        if (handle.isEmpty() || password.isEmpty()) {
            view.displayButton(false);
        }
        else {
            view.displayButton(true);
        }
    }

    public void checkUser(String handle) {
        new VerifyUserAsync(handle).execute();
    }
}
