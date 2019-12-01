package response;

import Model.Status;

import java.util.List;

public class GetUserFeedResponse {
    public List<Status> statuses;
    public String message;
    public String lastKey; //


    public GetUserFeedResponse(List<Status> statuses, String message, String lastKey) {
        this.statuses = statuses;
        this.message = message;
        this.lastKey = lastKey;
    }

}
