package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.FeedFragmentMVP;
import com.example.clutter.Model.ModelSingleton;
import com.example.clutter.Model.Status;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.FeedFragment;
import com.example.clutter.sdk.model.StatusList;
import com.example.clutter.sdk.model.StatusListStatusesItem;

import java.util.ArrayList;
import java.util.List;

public class FeedPresenter implements FeedFragmentMVP.Presenter {
    private FeedFragment feedView;

    private class GetFeedAsync extends AsyncTask<Void, Void, List<Status>> {

        private GetFeedAsync() {
            //Blank constructor
        }

        @Override
        protected List<com.example.clutter.Model.Status> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            StatusList listOfStatuses = proxy.getFeed(ModelSingleton.getmUser().getUserHandle());

            List<StatusListStatusesItem> statusItems = listOfStatuses.getStatuses();
            List<com.example.clutter.Model.Status> statusesToPost = new ArrayList<>();

            // For each of the JSON status items returned from AWS, parse into model Status object
            // If there is no feed to see, detect it so we don't call null object reference on statusItems
            if (listOfStatuses.getStatuses() == null) {
                return null;
            }

            else {
                for (int i = 0; i < statusItems.size() ; i++) {
                    StatusListStatusesItem currentStatus = statusItems.get(i);

                    String profilePic = currentStatus.getProfilePic();
                    String firstName = currentStatus.getFirstName();
                    String userHandle = currentStatus.getUserHandle();
                    String time = currentStatus.getTime();
                    String status = currentStatus.getStatus();
                    String imageAttachment = currentStatus.getImageAttachment();
                    String videoAttachment = currentStatus.getVideoAttachment();

                    com.example.clutter.Model.Status clientStatus = new com.example.clutter.Model.Status(profilePic, firstName, userHandle,
                            time, status, imageAttachment, videoAttachment);
                    statusesToPost.add(clientStatus);
                }

                return statusesToPost;
            }
        }

        @Override
        protected void onPostExecute(List<com.example.clutter.Model.Status> statuses) {
            if (statuses == null) {
                feedView.emptyFeed();
            }
            else {
                feedView.displayStatus(statuses);
            }
        }
    }

    public FeedPresenter(FeedFragment view) {
        this.feedView = view;
    }

    @Override
    public void createDummyData() {
        new GetFeedAsync().execute();
    }
}