package handler;

import DatabaseAccess.AuthenticationDAO;
import DatabaseAccess.UserDAO;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import request.SignInRequest;
import response.SignInResponse;

public class SignInHandler {
    public SignInResponse handleRequest(SignInRequest request, Context context) {
        LambdaLogger logger = context.getLogger();
        UserDAO userDAO = new UserDAO();


        SignInResponse response = userDAO.signInUser(request.getUserhandle(), request.getPassword());

        // Successful sign-in, store authtoken in Authentication table
        if (response.getAuthToken() != null) {
            AuthenticationDAO authDAO = new AuthenticationDAO();
            authDAO.authenticateUser(request.getUserhandle(), response.getAuthToken());
        }

        return response;
    }
}
