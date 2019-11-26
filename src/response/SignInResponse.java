package response;

public class SignInResponse {
    public String message;
    public String userhandle;
    public String authToken;

    public SignInResponse(String message, String userhandle, String authToken) {
        this.message = message;
        this.userhandle = userhandle;
        this.authToken = authToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
