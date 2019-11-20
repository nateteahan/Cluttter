package response;

import Model.Status;

import java.util.List;

public class GetUserStoryResponse {
    public List<Status> statuses;
    public String message;

    public GetUserStoryResponse(List<Status> userStory, String message) {
        this.statuses = userStory;
        this.message = message;
    }
}
