package com.example.clutter.Presenter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ImageView;

import com.example.clutter.InterfaceMVP.FeedFragmentMVP;
import com.example.clutter.Model.Status;
import com.example.clutter.R;
import com.example.clutter.View.FeedFragment;

import java.util.ArrayList;
import java.util.List;

public class FeedPresenter implements FeedFragmentMVP.Presenter {
    private FeedFragment feedView;

    public FeedPresenter(FeedFragment view) {
        this.feedView = view;
    }

    @Override
    public void createDummyData() {
        List<Status> statuses = new ArrayList<>();
        Status status;

//        Drawable userPic = feedView.getResources().getDrawable(R.drawable.me);
//        ImageView icon =

//        Drawable drawable = feedView.getResources().getDrawable(R.drawable.camera_logo);
        Drawable drawable = ResourcesCompat.getDrawable(feedView.getResources(), R.drawable.camera_logo, null);
        ImageView imageView = new ImageView(feedView.getActivity());
        imageView.setImageDrawable(drawable);

        status = new Status("Nate", "@nateteahan", "4:20 a.m.", "Man I don't know why I am still awake", imageView, null);
        statuses.add(status);

        status = new Status("Rocky", "@rockyevans", "6:53 p.m.", "My #CS 312 project ruined me today", null, null);
        statuses.add(status);

        status = new Status("Justin", "@justinj_hunt", "8:00 p.m.", "I am going to go to bed early tonight,", null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "4:20 a.m.", "Man I don't know why I am still awake",null, null);
        statuses.add(status);

        status = new Status("Cayla", "@caylarob", "6:53 p.m.", "Yesterday, @cayla and I went to the grocery store and decided" +
                " that I needed some raspberries. I went to the fruit aisle and it turned out they had all gone bad already. Thanks @smiths", null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "4:20 a.m.", "Man I don't know why I am still awake", null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "6:53 p.m.", "My CS 312 project ruined me today",null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight",null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "4:20 a.m.", "Man I don't know why I am still awake",null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "6:53 p.m.", "My CS 312 project ruined me today",null, null);
        statuses.add(status);

        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight",null, null);
        statuses.add(status);

        feedView.displayStatus(statuses);
    }
}
