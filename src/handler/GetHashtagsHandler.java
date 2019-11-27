package handler;

import DatabaseAccess.HashtagDAO;
import Model.Status;
import request.GetHashtagsRequest;
import response.GetHashtagsResponse;

import java.util.ArrayList;

public class GetHashtagsHandler {
    private ArrayList<Status> statuses;
    private Status status;

    public GetHashtagsResponse handleHashtags(GetHashtagsRequest request) {
        HashtagDAO hashtagDAO = new HashtagDAO();
        GetHashtagsResponse response = hashtagDAO.getHashtags(request);

        return response;
    }
}
