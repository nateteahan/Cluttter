package com.example.clutter.InterfaceMVP;
import com.example.clutter.Model.Status;

import java.util.List;

public interface StoryFragmentMVP {
    interface Model {

    }

    interface View {
        void displayStories(List<Status> statuses, String key);
    }

    interface Presenter {
        void createDummyData(List<Status> statuses, String handle, String key);
    }
}
