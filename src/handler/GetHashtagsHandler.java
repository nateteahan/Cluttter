package handler;

import Model.Status;
import request.GetHashtagsRequest;
import response.GetHashtagsResponse;

import java.util.ArrayList;

public class GetHashtagsHandler {
    private ArrayList<Status> statuses;
    private Status status;

    public GetHashtagsResponse handleHashtags(GetHashtagsRequest request) {
        statuses =  new ArrayList<>();

        status = new Status("http://i.imgur.com/bIRGzVO.jpg","Rocky", "@roscoe_evans", "6:53 p.m.", "My #CS 312 project ruined me today", null, null);
        statuses.add(status);


        status = new Status("https://kiteloft.com/media/catalog/product/cache/1/image/728x/17f82f742ffe127f42dca9de82fb58b1/d/a/davedes2_1.jpg","Samuel", "@samhansen", "3:31 p.m.", "I am so happy to be working on something other than a #CS project", null, null);
        statuses.add(status);

        GetHashtagsResponse response = new GetHashtagsResponse(statuses, null);

        return response;
    }
}
