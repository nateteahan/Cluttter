package com.example.clutter.Presenter;

import com.example.clutter.InterfaceMVP.AccountFragmentMVP;
import com.example.clutter.View.AccountFragment;

import java.util.ArrayList;
import java.util.List;

import com.example.clutter.Model.FollowInfo;

public class AccountPresenter implements AccountFragmentMVP.Presenter {
    private AccountFragment view;
    private List<FollowInfo> info;

    public AccountPresenter(AccountFragment view) {
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

//        view.displayInfo(info);
    }

    @Override
    public void createDummyFollowees() {
        info.clear();

        FollowInfo followee = new FollowInfo("God");
        info.add(followee);

//        view.displayInfo(info);
    }
}
