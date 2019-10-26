package com.example.clutter.InterfaceMVP;
import java.util.List;

import com.example.clutter.Model.Status;

public interface StoryFragmentMVP {
    interface Model {

    }

    interface View {
        void displayStories(List<Status> statuses);
    }

    interface Presenter {
        void createDummyData();
    }
}
