package handler;

import DatabaseAccess.UserDAO;
import request.PostPictureRequest;
import response.PostPictureResponse;

public class PostPictureHandler {
    public PostPictureResponse pictureHandler(PostPictureRequest request) {

        UserDAO userDAO = new UserDAO();
        String message = userDAO.updateUser("@roscoe_evans", "http://i.imgur.com/bIRGzVO.jpg");

        PostPictureResponse response = new PostPictureResponse(message);

        return response;
    }
}
