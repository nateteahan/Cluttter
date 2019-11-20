package response;

public class GetUserResponse {
    public String profilePic;
    public String userHandle;
    public String firstName;
    public String lastName;
    public String email;
    public String message;

    public GetUserResponse(String userHandle, String firstName, String lastName, String email, String profilePic, String message) {
        this.profilePic = profilePic;
        this.userHandle = userHandle;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.message = message;
    }
}
