package handler;

import DatabaseAccess.FollowDAO;
import request.IsFollowingRequest;
import response.IsFollowingResponse;

public class IsFollowingHandler {
    public IsFollowingResponse handleRequest(IsFollowingRequest request) {
        FollowDAO followDAO = new FollowDAO();

        return followDAO.isFollowing(request);
    }
}
