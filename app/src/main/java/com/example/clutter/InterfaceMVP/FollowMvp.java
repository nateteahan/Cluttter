package com.example.clutter.InterfaceMVP;

import com.example.clutter.Model.FollowInfo;
import com.example.clutter.Model.Status;

import java.util.List;

public interface FollowMvp {
    interface Model {

    }

    interface View {
        void displayFollowInfo(List<FollowInfo> stories);
    }

    interface Presenter {
        void createDummyFollowers();
        void createDummyFollowees();
    }
}
