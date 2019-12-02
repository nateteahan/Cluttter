package com.example.clutter.InterfaceMVP;

import com.example.clutter.Model.Status;
import com.example.clutter.Model.UserInfo;

import java.util.List;

public interface UserMVP {

    interface Model {

    }

    interface View {
        void displayStatuses(List<Status> statuses, String key);
        void assignUserFields(UserInfo info);
    }

    interface Presenter {
        void createDummyData(List<Status> statuses, String handle, String key);
    }
}
