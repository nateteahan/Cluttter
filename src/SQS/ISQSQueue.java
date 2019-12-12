package SQS;

import Model.PostStatusRequest;
import request.SendStatusRequest;

public interface ISQSQueue {
    void GetFollowers(SendStatusRequest request, String QueueURL);
    void PostStatus (PostStatusRequest request, String QueueURL);
}
