package response;

public class IsFollowingResponse {
    public String message;

    public IsFollowingResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
