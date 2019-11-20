package response;

import Model.Status;

import java.util.List;

public class GetHashtagsResponse {
    public List<Status> statuses;
    public String message;

    public GetHashtagsResponse(List<Status> statuses, String message) {
        this.statuses = statuses;
        this.message = message;
    }
}
