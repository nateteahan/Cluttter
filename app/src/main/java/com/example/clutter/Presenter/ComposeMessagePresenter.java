package com.example.clutter.Presenter;

import android.os.AsyncTask;

import com.example.clutter.InterfaceMVP.ComposeMessageMVP;
import com.example.clutter.ServerProxy.ServerProxy;
import com.example.clutter.View.ComposeMessageActivity;
import com.example.clutter.sdk.model.Empty;
import com.example.clutter.sdk.model.Hashtag;
import com.example.clutter.sdk.model.Message;
import com.example.clutter.sdk.model.SendStatusRequest;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComposeMessagePresenter implements ComposeMessageMVP.Presenter {
    private ComposeMessageActivity view;
    private SendStatusRequest status;

    private class PostStatusAsync extends AsyncTask<Void, Void, String> {

        public PostStatusAsync(SendStatusRequest currentStatus) {
            status = currentStatus;
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Call aws post status function
            // Parse JSON
            /* FIXME --> parse status message to see if there exists hashtags, if so, call proxy.addHashtags to Hashtag table for each one */
            ServerProxy proxy = new ServerProxy();
            Message statusResult = proxy.postStatus(status.getUserHandle(), status);

            // Status successfully posted to Feed/Story tables, parse for hashtags, add them to Hashtag table
            if (statusResult.getMessage().equals("Successfully posted status!")) {
                String patternst = "#[a-zA-Z0-9]*";
                Pattern pattern = Pattern.compile(patternst);
                Matcher matcher = pattern.matcher(status.getStatus());
                Set<String> hashtags = new HashSet<>();

                while (matcher.find()) {
                    hashtags.add(matcher.group(0).replace("#", ""));
                }

                if (hashtags.size() > 0) {
                    for (String hashtag: hashtags) {
                        Hashtag tag = new Hashtag();
                        tag.setHashtag(hashtag);
                        tag.setFirstName(status.getFirstName());
                        tag.setProfilePic(status.getProfilePic());
                        tag.setStatus(status.getStatus());
                        tag.setTime(status.getTime());
                        tag.setUserHandle(status.getUserHandle());
                        tag.setImageAttachment(status.getImageAttachment());
                        tag.setVideoAttachment(status.getVideoAttachment());

                        Empty empty = proxy.postHashtag(hashtag, tag);
                    }
                }

            }

            return statusResult.getMessage();
        }

        @Override
        protected void onPostExecute(String s) {
            view.displayMessage(s);
        }
    }

    public ComposeMessagePresenter(ComposeMessageActivity view) {
        this.view = view;
    }

    public void checkCharacterCount(String message) {
        int characterCount = 0;

        for (int i = 0; i < message.length(); i++) {
            characterCount += 1;
            String charsRemaining = "Characters: " + (100 - characterCount);
            view.displayCharacterCount(charsRemaining);

            if (characterCount > 100) {
                view.displayMessage("You have exceeded the maximum character count of 250. Your followers don't care that much.");
                break;
            }
        }
    }

    public void checkValidInput(SendStatusRequest status) {
        new PostStatusAsync(status).execute();
    }
}
