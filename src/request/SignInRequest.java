package request;

public class SignInRequest {
    public String userhandle;
    public String password;

    public String getUserhandle() {
        return userhandle;
    }

    public void setUserhandle(String userhandle) {
        this.userhandle = userhandle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
