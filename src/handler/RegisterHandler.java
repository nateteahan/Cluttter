package handler;

import DatabaseAccess.UserDAO;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import request.RegisterRequest;
import response.GetUserResponse;
import response.RegisterResponse;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class RegisterHandler {
    public RegisterResponse handleRequest(RegisterRequest request, Context context) {
        UserDAO userDAO = new UserDAO();
        String message;
        LambdaLogger logger = context.getLogger();

        GetUserResponse userResponse = userDAO.getUserInfo(request.getUserhandle(), context);
        // User already exists
        if (userResponse.message == null) {
            message = "User already exists.";
        }
        else {
            try {
                message = userDAO.createUser(request.getFirstName(), request.getLastName(), request.getEmail(),
                        request.getUserhandle(), request.getPassword(), request.getProfilePic(), context);
            } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                message = e.toString();
                e.printStackTrace();
            }
        }
        return new RegisterResponse(message);
    }
}
