package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.StoryFragmentMVP;
import com.example.clutter.Model.Status;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.AccountFragment;
import com.example.clutter.sdk.model.NewStatusList;
import com.example.clutter.sdk.model.NewStatusListStatusesItem;

import java.util.List;

//import com.example.clutter.View.StoryFragment;

public class StoryPresenter implements StoryFragmentMVP.Presenter {
    private AccountFragment view;
    private String userhandle;
    private String lastKey;
    private List<Status> statusesToPost;

    private class GetStoryAsync extends AsyncTask<Void, Void, List<Status>> {
        public GetStoryAsync(String Handle, String key) {
            userhandle = Handle;
            lastKey = key;
        }

        @Override
        protected List<com.example.clutter.Model.Status> doInBackground(Void... voids) {
            ServerProxy proxy = new ServerProxy();

            if (lastKey == null) {
                lastKey = "";
            }

            NewStatusList storyStatuses = proxy.getUserStory(userhandle, lastKey);
            lastKey = storyStatuses.getLastKey();

            List<NewStatusListStatusesItem> statusItems = storyStatuses.getStatuses();

            if (statusItems == null) {
                return null;
            }
            else {
                // For each of the JSON status items returned from AWS, parse into model Status object
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
//            super.onPostExecute(aVoid);
            if (statuses == null) {
                view.emptyStories();
            }
            else {
                view.displayStories(statuses, lastKey);
            }
        }
    }

    public StoryPresenter(AccountFragment view) {
        this.view = view;
    }

    public void createDummyData(List<Status> statuses, String handle, String key) {
        this.userhandle = handle;

        if (key != null) {
            key = parseLastKey(key);
        }
        this.statusesToPost = statuses;
        new GetStoryAsync(handle, key).execute();
    }

    private String parseLastKey(String key) {
        //Remove special characters... Invalid in api gateway
        key = key.replaceAll(" ", "");
        key = key.replaceAll("/", "");
        key = key.replaceAll(":", "");

        return key;
    }
}
