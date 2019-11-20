package handler;

import DatabaseAccess.UserDAO;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import request.GetUserRequest;
import response.GetUserResponse;

public class GetUserHandler {
    public GetUserResponse handleRequest(GetUserRequest request, Context context) {
        UserDAO userDAO = new UserDAO();
        LambdaLogger logger = context.getLogger();
        logger.log("Request handle is: " + request.getUserhandle());

        GetUserResponse response = userDAO.getUser(request.getUserhandle(), context);

        if (response.message != null) {
            return new GetUserResponse(null, null, null, null, null, "User not found");
        }
        return response;
    }
}
