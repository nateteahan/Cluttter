package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.StoryFragmentMVP;
import com.example.clutter.Model.Status;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.AccountFragment;
import com.example.clutter.sdk.model.StatusList;
import com.example.clutter.sdk.model.StatusListStatusesItem;

import java.util.ArrayList;
import java.util.List;

//import com.example.clutter.View.StoryFragment;

public class StoryPresenter implements StoryFragmentMVP.Presenter {
    private AccountFragment view;

    private class GetStoryAsync extends AsyncTask<Void, Void, List<Status>> {

        public GetStoryAsync() {
            // Blank Constructor
        }

        @Override
        protected List<com.example.clutter.Model.Status> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            StatusList storyStatuses = proxy.getUserStory();

            List<StatusListStatusesItem> statusItems = storyStatuses.getStatuses();
            List<com.example.clutter.Model.Status> statusesToPost = new ArrayList<>();

            // For each of the JSON status items returned from AWS, parse into model Status object
            for (int i = 0; i < statusItems.size() ; i++) {
                StatusListStatusesItem currentStatus = statusItems.get(i);

                String firstName = currentStatus.getFirstName();
                String userHandle = currentStatus.getUserHandle();
                String time = currentStatus.getTime();
                String status = currentStatus.getStatus();
                String imageAttachment = currentStatus.getImageAttachment();
                String videoAttachment = currentStatus.getVideoAttachment();

                com.example.clutter.Model.Status clientStatus = new com.example.clutter.Model.Status(firstName, userHandle,
                        time, status, imageAttachment, videoAttachment);
                statusesToPost.add(clientStatus);
            }

//            statuses = statusesToPost;
            return statusesToPost;

        }

        @Override
        protected void onPostExecute(List<com.example.clutter.Model.Status> statuses) {
//            super.onPostExecute(aVoid);
            view.displayStories(statuses);
        }
    }

    public StoryPresenter(AccountFragment view) {
        this.view = view;
    }

    public void createDummyData() {
        new GetStoryAsync().execute();
//        List<Status> statuses = new ArrayList<>();
//        Status status;
//
////        Drawable userPic = feedView.getResources().getDrawable(R.drawable.me);
////        ImageView icon =
//        status = new Status("Nate", "@nateteahan", "4:20 a.m.", "Man I don't know why I am still awake", null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "6:53 p.m.", "My CS 312 project ruined me today", null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
//        statuses.add(status);
//
//        status = new Status("Nate", "@nateteahan", "8:00 p.m.", "I am going to go to bed early tonight", null, null);
//        statuses.add(status);
//
//        view.displayStories(statuses);
    }
}
