package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.SignInMVP;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.SignInActivity;
import com.example.clutter.sdk.model.Authorization;
import com.example.clutter.sdk.model.SignInUser;
import com.example.clutter.sdk.model.User;

public class SignInPresenter implements SignInMVP.Presenter {
    private SignInActivity view;
    private com.example.clutter.Model.User rootUser;

    private class SignInAsync extends AsyncTask<Void, Void, Authorization> {
        private SignInUser user = new SignInUser();

        private SignInAsync(SignInUser user) {
            this.user = user;
        }

        @Override
        protected Authorization doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            Authorization authorization = proxy.signIn(user);

            // Sign-in successful, get the user to set to the Singleton
            if (authorization.getMessage() == null) {
                User user1 = proxy.getUser(user.getUserhandle());

                //GetUserFeed

                rootUser = new com.example.clutter.Model.User(user1.getUserHandle(), user1.getFirstName(), user1.getLastName(), user1.getEmail());
                rootUser.setProfilePic(user1.getProfilePic());
                rootUser.setAuthToken(authorization.getAuthToken());
            }

            return authorization;
        }

        @Override
        protected void onPostExecute(Authorization authorization) {
            //Correct credentials given
            if (authorization.getMessage() == null) {
                view.setSingletonUser(rootUser);
                view.successfulLogin();
            }
            else {
                view.displayError("Incorrect credentials. Try again.");
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

    @Override
    public void signIn(SignInUser user) {
        new SignInAsync(user).execute();
    }

}
