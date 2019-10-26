package com.example.clutter.InterfaceMVP;

import java.util.List;

import com.example.clutter.Model.Status;

public interface AccountFragmentMVP {
    interface Model {

    }

    interface View {
        void displayStories(List<Status> stories);
    }

    interface Presenter {
        void createDummyFollowers();
        void createDummyFollowees();
    }
}
