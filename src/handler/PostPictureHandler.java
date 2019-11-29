package handler;

import DatabaseAccess.UserDAO;
import com.amazonaws.services.lambda.runtime.Context;
import request.PostPictureRequest;
import response.PostPictureResponse;

public class PostPictureHandler {
    public PostPictureResponse pictureHandler(PostPictureRequest request, Context context) {

        UserDAO userDAO = new UserDAO();
        String message = userDAO.updateUser(request.getUserhandle(), request.getProfilePic(), context);

        PostPictureResponse response = new PostPictureResponse(message);

        return response;
    }
}
