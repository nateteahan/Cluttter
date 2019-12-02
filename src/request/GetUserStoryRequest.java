package request;

public class GetUserStoryRequest {
    public String userhandle;
    public String lastKey;

    public String getUserHandle() {
        return userhandle;
    }

    public void setUserHandle(String userHandle) {
        this.userhandle = userHandle;
    }

    public String getLastKey() {
        return lastKey;
    }

    public void setLastKey(String lastKey) {
        this.lastKey = lastKey;
    }
}
