package com.example.clutter.Presenter;

import com.example.clutter.InterfaceMVP.FollowMvp;
import com.example.clutter.Model.FollowInfo;
import com.example.clutter.View.FollowingFragment;

import java.util.ArrayList;
import java.util.List;

public class FollowingPresenter implements FollowMvp.Presenter {
    private FollowingFragment view;
    private List<FollowInfo> info;

    public FollowingPresenter(FollowingFragment followingFragment) {
        this.view = followingFragment;
        this.info = new ArrayList<>();
    }

    @Override
    public void createDummyFollowers() {
        //
    }

    @Override
    public void createDummyFollowees() {
                info.clear();

        FollowInfo followee = new FollowInfo("God");
        info.add(followee);

        view.displayFollowInfo(info);
    }
}
