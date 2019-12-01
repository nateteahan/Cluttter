package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.FollowMvp;
import com.example.clutter.Model.FollowInfo;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.FollowerFragment;
import com.example.clutter.sdk.model.GetFollowers;
import com.example.clutter.sdk.model.GetFollowersFollowersItem;

import java.util.ArrayList;
import java.util.List;

public class FollowerPresenter implements FollowMvp.Presenter {
    private FollowerFragment view;
    private List<FollowInfo> info;
    private String userHandle;
    private String lastKey;
    private List<FollowInfo> followersToDisplay;

    private class GetFollowersAsync extends AsyncTask<Void, Void, List<FollowInfo>> {

        private GetFollowersAsync(String handle, String key) {
            userHandle = handle;
            lastKey = key;
        }

        @Override
        protected List<FollowInfo> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();

            GetFollowers listOfFollowers = proxy.getFollowers(userHandle, lastKey);

            List<GetFollowersFollowersItem> followerItem = listOfFollowers.getFollowers();
            lastKey = listOfFollowers.getLastKey();

            // FIXME --> if no followers are found, return a message or inflate a TextView
            // For each of the JSON follow items returned from AWS, parse into model FollowInfo object
            if (followerItem != null) {
                for (int i = 0; i < followerItem.size(); i++) {
                    GetFollowersFollowersItem currentFollowing = followerItem.get(i);

                    String profilePic = proxy.getUser(currentFollowing.getUserHandle()).getProfilePic();
                    String userHandle = "@" + currentFollowing.getUserHandle();
                    FollowInfo followingObject = new FollowInfo(profilePic,userHandle);
                    followersToDisplay.add(followingObject);
                }
            }
            return followersToDisplay;
        }

        @Override
        protected void onPostExecute(List<FollowInfo> following) {
            view.displayFollowInfo(following, lastKey);
        }
    }

    public FollowerPresenter(FollowerFragment view) {
        this.view = view;
        this.info = new ArrayList<>();
    }

    @Override
    public void getFollowers(List<FollowInfo> followers, String handle, String key) {
        this.followersToDisplay = followers;
        new GetFollowersAsync(handle, key).execute();
    }

    @Override
    public void getFollowees(List<FollowInfo> following, String handle, String key) {
        // Leave blank for FollowingPresenter
    }
}
