package com.example.clutter.Presenter;

import com.example.clutter.InterfaceMVP.StoryFragmentMVP;
import com.example.clutter.View.AccountFragment;
//import com.example.clutter.View.StoryFragment;

import java.util.ArrayList;
import java.util.List;

import com.example.clutter.Model.Status;

public class StoryPresenter implements StoryFragmentMVP.Presenter {
    private AccountFragment view;

    public StoryPresenter(AccountFragment view) {
        this.view = view;
    }

    public void createDummyData() {
        List<Status> statuses = new ArrayList<>();
        Status status;

//        Drawable userPic = feedView.getResources().getDrawable(R.drawable.me);
//        ImageView icon =
        status = new Status("Nate", "@nateteahan", "4:20 a.m.", "Man I don't know why I am still awake", null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "6:53 p.m.", "My CS 312 project ruined me today", null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
        statuses.add(status);

        view.displayStories(statuses);
    }
}
