package com.example.clutter.InterfaceMVP;

import com.example.clutter.Model.FollowInfo;

import java.util.List;

public interface FollowMvp {
    interface Model {

    }

    interface View {
        void displayFollowInfo(List<FollowInfo> stories);
    }

    interface Presenter {
        void getFollowers(String handle);
        void getFollowees(String handle);
    }
}
