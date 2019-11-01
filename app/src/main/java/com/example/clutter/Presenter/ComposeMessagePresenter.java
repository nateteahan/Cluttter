package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.ComposeMessageMVP;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.ComposeMessageActivity;
import com.example.clutter.sdk.model.Message;

public class ComposeMessagePresenter implements ComposeMessageMVP.Presenter {
    private ComposeMessageActivity view;

    private class GetSuccessMessageAsync extends AsyncTask<Void, Void, String> {

        public GetSuccessMessageAsync() {
            //Blank constructor
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Call aws post status function
            // Parse JSON
            ServerProxy proxy = new ServerProxy();
            Message statusResult = proxy.postStatus();

            return statusResult.getMessage();
        }

        @Override
        protected void onPostExecute(String s) {
            view.displayMessage(s);
        }
    }

    public ComposeMessagePresenter(ComposeMessageActivity view) {
        this.view = view;
    }

    public void checkCharacterCount(String message) {
        int characterCount = 0;

        for (int i = 0; i < message.length(); i++) {
            characterCount += 1;
            String charsRemaining = "Characters: " + (100 - characterCount);
            view.displayCharacterCount(charsRemaining);

            if (characterCount > 100) {
                view.displayMessage("You have exceeded the maximum character count of 250. Your followers don't care that much.");
                break;
            }
        }
    }

    public void checkValidInput(String message) {
//        if (message.length() > 250) {
//            view.displayMessage("Unsuccessful upload");
//        }
//        else {
//            view.displayMessage("Successful Upload");
//        }
        new GetSuccessMessageAsync().execute();
    }
}
