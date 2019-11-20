package response;

import Model.Status;

import java.util.List;

public class GetUserFeedResponse {
    public List<Status> statuses;
    public String message;


    public GetUserFeedResponse(List<Status> statuses, String message) {
        this.statuses = statuses;
        this.message = message;
    }


}
