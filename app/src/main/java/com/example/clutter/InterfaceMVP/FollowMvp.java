package com.example.clutter.InterfaceMVP;

import com.example.clutter.Model.FollowInfo;

import java.util.List;

public interface FollowMvp {
    interface Model {

    }

    interface View {
        void displayFollowInfo(List<FollowInfo> stories, String lastKey);
    }

    interface Presenter {
        void getFollowers(List<FollowInfo> followers, String handle, String lastKey);
        void getFollowees(List<FollowInfo> following, String handle, String lastKey);
    }
}
