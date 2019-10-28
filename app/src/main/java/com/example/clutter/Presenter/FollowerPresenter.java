package com.example.clutter.Presenter;

import com.example.clutter.InterfaceMVP.FollowMvp;
import com.example.clutter.Model.FollowInfo;
import com.example.clutter.View.FollowActivity;
import com.example.clutter.View.FollowerFragment;

import java.util.ArrayList;
import java.util.List;

public class FollowerPresenter implements FollowMvp.Presenter {
    private FollowerFragment view;
    private List<FollowInfo> info;

    public FollowerPresenter(FollowerFragment view) {
        this.view = view;
        this.info = new ArrayList<>();
    }

    @Override
    public void createDummyFollowers() {
        info.clear();

        FollowInfo follower = new FollowInfo("Justin");
        info.add(follower);

        follower = new FollowInfo("Rocky");
        info.add(follower);

        view.displayFollowInfo(info);
    }

    @Override
    public void createDummyFollowees() {
        // Leave blank for FollowingPresenter
    }
}
