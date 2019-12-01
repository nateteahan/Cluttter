package response;

public class RegisterResponse {
    public String message;
    public String userhandle;
    public String authToken;

    public RegisterResponse(String message) {
        this.message = message;
//        this.userhandle = userhandle;
//        this.authToken = authToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserhandle() {
        return userhandle;
    }

    public void setUserhandle(String userhandle) {
        this.userhandle = userhandle;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
