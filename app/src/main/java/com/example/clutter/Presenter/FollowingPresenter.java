package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.FollowMvp;
import com.example.clutter.Model.FollowInfo;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.FollowingFragment;
import com.example.clutter.sdk.model.GetFollowing;
import com.example.clutter.sdk.model.GetFollowingFollowingItem;

import java.util.ArrayList;
import java.util.List;

public class FollowingPresenter implements FollowMvp.Presenter {
    private FollowingFragment view;
    private List<FollowInfo> info;
    private String userHandle;
    private String lastKey;
    private List<FollowInfo> followingToDisplay;

    private class GetFollowingAsync extends AsyncTask<Void, Void, List<FollowInfo>> {

        private GetFollowingAsync(String handle, String key) {
            userHandle = handle.replaceAll("@", "");
            lastKey = key;
        }

        @Override
        protected List<FollowInfo> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            GetFollowing listOfFollowing = proxy.getFollowing(userHandle, lastKey);
            lastKey = listOfFollowing.getLastKey();

            List<GetFollowingFollowingItem> followingItem = listOfFollowing.getFollowing();

            // For each of the JSON follow items returned from AWS, parse into model FollowInfo object
            if (followingItem != null) {
                for (int i = 0; i < followingItem.size() ; i++) {
                   GetFollowingFollowingItem currentFollowing = followingItem.get(i);

                    String profilePic = proxy.getUser(currentFollowing.getUserHandle()).getProfilePic();
                    String userHandle = "@" + currentFollowing.getUserHandle();
                    FollowInfo followingObject = new FollowInfo(profilePic, userHandle);
                    followingToDisplay.add(followingObject);
                }
            }
            return followingToDisplay;
        }

        @Override
        protected void onPostExecute(List<FollowInfo> following) {
            view.displayFollowInfo(following, lastKey);
        }
    }

    public FollowingPresenter(FollowingFragment followingFragment) {
        this.view = followingFragment;
        this.info = new ArrayList<>();
    }

    @Override
    public void getFollowers(List<FollowInfo> followers, String handle, String key) {
        //
    }

    @Override
    public void getFollowees(List<FollowInfo> following, String handle, String key) {
        this.followingToDisplay = following;

        new GetFollowingAsync(handle, key).execute();
    }
}
