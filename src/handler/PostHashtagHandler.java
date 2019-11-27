package handler;

import DatabaseAccess.HashtagDAO;
import request.PostHashtagRequest;
import response.PostHashtagResponse;

public class PostHashtagHandler {
    public PostHashtagResponse handleHashtag(PostHashtagRequest request) {
        HashtagDAO hashtagDAO = new HashtagDAO();
        PostHashtagResponse response = hashtagDAO.postHashtag(request);

        return response;
    }
}
