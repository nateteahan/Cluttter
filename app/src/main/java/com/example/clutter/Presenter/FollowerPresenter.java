package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.FollowMvp;
import com.example.clutter.Model.FollowInfo;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.FollowerFragment;
import com.example.clutter.sdk.model.FollowerList;
import com.example.clutter.sdk.model.FollowerListFollowersItem;

import java.util.ArrayList;
import java.util.List;

public class FollowerPresenter implements FollowMvp.Presenter {
    private FollowerFragment view;
    private List<FollowInfo> info;
    private String userHandle;

    private class GetFollowersAsync extends AsyncTask<Void, Void, List<FollowInfo>> {

        private GetFollowersAsync(String handle) {
            userHandle = handle;
        }

        @Override
        protected List<FollowInfo> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            FollowerList listOfFollowers = proxy.getFollowers(userHandle);

            List<FollowerListFollowersItem> followerItem = listOfFollowers.getFollowers();
            List<FollowInfo> followingToDisplay = new ArrayList<>();

            // FIXME --> if no followers are found, return a message or inflate a TextView
            // For each of the JSON follow items returned from AWS, parse into model FollowInfo object
            if (followerItem != null) {
                for (int i = 0; i < followerItem.size() ; i++) {
                    FollowerListFollowersItem currentFollowing = followerItem.get(i);

//                    String profilePic = currentFollowing.getProfilePic();
                    String profilePic = proxy.getUser(currentFollowing.getUserHandle()).getProfilePic();
                    String userHandle = "@" + currentFollowing.getUserHandle();
                    FollowInfo followingObject = new FollowInfo(profilePic,userHandle);
                    followingToDisplay.add(followingObject);
                }
            }
            return followingToDisplay;
        }

        @Override
        protected void onPostExecute(List<FollowInfo> following) {
            view.displayFollowInfo(following);
        }
    }

    public FollowerPresenter(FollowerFragment view) {
        this.view = view;
        this.info = new ArrayList<>();
    }

    @Override
    public void getFollowers(String handle) {
        new GetFollowersAsync(handle).execute();
    }

    @Override
    public void getFollowees(String handle) {
        // Leave blank for FollowingPresenter
    }
}
