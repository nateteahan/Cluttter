package com.example.clutter.InterfaceMVP;

import com.example.clutter.sdk.model.SendStatusRequest;

public interface ComposeMessageMVP {
    interface Model {

    }

    interface View {
        void displayMessage(String message);
        void displayCharacterCount(String count);
    }

    interface Presenter {
        void checkCharacterCount(String message);
        void checkValidInput(SendStatusRequest status);
    }
}
