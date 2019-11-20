package handler;

import Model.Status;
import request.GetUserFeedRequest;
import response.GetUserStoryResponse;

import java.util.ArrayList;
import java.util.List;

public class GetUserStoryHandler {
    public GetUserStoryResponse handleUserStoryRequest(GetUserFeedRequest request) {
        List<Status> story = new ArrayList<>();
        Status status;

        status = new Status("http://i.imgur.com/bIRGzVO.jpg","Rocky", "@roscoe_evans", "6:53 p.m.", "My #CS 312 project ruined me today", "https://bangladhol.com/book_th/C29B5E81.jpg", null);
        story.add(status);

        GetUserStoryResponse response = new GetUserStoryResponse(story, null);

        return response;
    }
}
