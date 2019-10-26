package com.example.clutter.Presenter;

import com.example.clutter.InterfaceMVP.ComposeMessageMVP;
import com.example.clutter.View.ComposeMessageActivity;

public class ComposeMessagePresenter implements ComposeMessageMVP.Presenter {
    private ComposeMessageActivity view;

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
        if (message.length() > 250) {
            view.displayMessage("Unsuccessful upload");
        }
        else {
            view.displayMessage("Successful Upload");
        }
    }
}
