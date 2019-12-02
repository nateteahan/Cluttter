package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.FeedFragmentMVP;
import com.example.clutter.Model.Status;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.FeedFragment;
import com.example.clutter.sdk.model.NewStatusList;
import com.example.clutter.sdk.model.NewStatusListStatusesItem;

import java.util.List;

public class FeedPresenter implements FeedFragmentMVP.Presenter {
    private FeedFragment feedView;
    private String userHandle;
    private String lastKey;
    private List<Status> statusesToPost;

    private class GetFeedAsync extends AsyncTask<Void, Void, List<Status>> {

        private GetFeedAsync(String handle, String key) {
            userHandle = handle;
            lastKey = key;
        }

        @Override
        protected List<com.example.clutter.Model.Status> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();
            if (lastKey == null) {
                lastKey = "";
            }

            NewStatusList listOfStatuses = proxy.getFeed(userHandle, lastKey);
            lastKey = listOfStatuses.getLastKey();

            List<NewStatusListStatusesItem> statusItems = listOfStatuses.getStatuses();

            // For each of the JSON status items returned from AWS, parse into model Status object
            // If there is no feed to see, detect it so we don't call null object reference on statusItems
            if (listOfStatuses.getStatuses() == null) {
                return null;
            }

            else {
                for (int i = 0; i < statusItems.size() ; i++) {
                    NewStatusListStatusesItem currentStatus = statusItems.get(i);

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
            if (statuses.size() == 0) {
                feedView.emptyFeed();
            }
            else {
                feedView.displayStatus(statuses, lastKey);
            }
        }
    }

    public FeedPresenter(FeedFragment view) {
        this.feedView = view;
    }

    @Override
    public void createDummyData(List<Status> statuses, String userHandle, String lastKey) {
        this.userHandle = userHandle;

        if (lastKey != null) {
            lastKey = parseLastKey(lastKey);
        }

        this.statusesToPost = statuses;
        new GetFeedAsync(userHandle, lastKey).execute();
    }

    private String parseLastKey(String key) {
        if (key != null) {
            //Remove special characters... Invalid in api gateway
            key = key.replaceAll(" ", "");
            key = key.replaceAll("/", "");
            key = key.replaceAll(":", "");
        }

        return key;
    }

}