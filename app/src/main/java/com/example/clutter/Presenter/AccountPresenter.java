package com.example.clutter.Presenter;

import com.example.clutter.InterfaceMVP.AccountFragmentMVP;
import com.example.clutter.Model.FollowInfo;
import com.example.clutter.View.AccountFragment;

import java.util.ArrayList;
import java.util.List;

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

        FollowInfo follower = new FollowInfo("https://www.famousbirthdays.com/faces/hunt-director-justin-image.jpg","Justin");
        info.add(follower);

        follower = new FollowInfo("http://i.imgur.com/bIRGzVO.jpg", "Rocky");
        info.add(follower);

//        view.displayInfo(info);
    }

    @Override
    public void createDummyFollowees() {
        info.clear();

        FollowInfo followee = new FollowInfo("http://i.imgur.com/bIRGzVO.jpg", "God");
        info.add(followee);

//        view.displayInfo(info);
    }
}
