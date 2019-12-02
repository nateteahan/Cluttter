package response;

import Model.Status;

import java.util.List;

public class GetUserStoryResponse {
    public List<Status> statuses;
    public String message;
    public String lastKey;

    public GetUserStoryResponse(List<Status> userStory, String message, String lastKey) {
        this.statuses = userStory;
        this.message = message;
        this.lastKey = lastKey;
    }
}
