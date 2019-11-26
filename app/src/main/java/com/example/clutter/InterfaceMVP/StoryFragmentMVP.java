package com.example.clutter.InterfaceMVP;
import com.example.clutter.Model.Status;

import java.util.List;

public interface StoryFragmentMVP {
    interface Model {

    }

    interface View {
        void displayStories(List<Status> statuses);
    }

    interface Presenter {
        void createDummyData(String handle);
    }
}
