package com.example.clutter.Model;

public class ModelSingleton {
    private static User mUser;
    private static String mEmail;
    private static String mPassword;
    private static final ModelSingleton INSTANCE = new ModelSingleton();

    public static User getmUser() {
        return mUser;
    }

    public static void setmUser(User mUser) {
        ModelSingleton.mUser = mUser;
    }

    public static String getmEmail() {
        return mEmail;
    }

    public static void setmEmail(String mEmail) {
        ModelSingleton.mEmail = mEmail;
    }

    public static String getmPassword() {
        return mPassword;
    }

    public static void setmPassword(String mPassword) {
        ModelSingleton.mPassword = mPassword;
    }

    public static ModelSingleton getINSTANCE() {
        return INSTANCE;
    }
}
