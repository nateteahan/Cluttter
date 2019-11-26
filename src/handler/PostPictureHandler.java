package handler;

import DatabaseAccess.UserDAO;
import request.PostPictureRequest;
import response.PostPictureResponse;

public class PostPictureHandler {
    public PostPictureResponse pictureHandler(PostPictureRequest request) {

        UserDAO userDAO = new UserDAO();
        String message = userDAO.updateUser(request.getUserhandle(), request.getProfilePic());

        PostPictureResponse response = new PostPictureResponse(message);

        return response;
    }
}
