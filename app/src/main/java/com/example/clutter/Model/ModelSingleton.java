package com.example.clutter.Model;

public class ModelSingleton {
    private static User mUser;
    private static final ModelSingleton INSTANCE = new ModelSingleton();

    public static User getmUser() {
        return mUser;
    }

    public static void setmUser(User mUser) {
        ModelSingleton.mUser = mUser;
    }

    public static ModelSingleton getINSTANCE() {
        return INSTANCE;
    }
}
