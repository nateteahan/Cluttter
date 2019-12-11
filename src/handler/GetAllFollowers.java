package handler;

import DatabaseAccess.FollowDAO;
import DatabaseAccess.StatusDAO;
import Model.FollowInfo;
import Model.PostStatusRequest;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import request.GetFollowersRequest;
import request.SendStatusRequest;
import response.GetFollowersResponse;

import java.util.ArrayList;
import java.util.List;

public class GetAllFollowers implements RequestHandler<SQSEvent, Void> {
    private final String FeedTableName = "Feed";
    @Override
    public Void handleRequest(SQSEvent event, Context context) {

        Gson gson = new Gson();
        SendStatusRequest request = new SendStatusRequest();

        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            request = gson.fromJson(msg.getBody(), SendStatusRequest.class);
        }

        String author = request.getUserhandle();

        // Create a GetFollowersRequest with the author as the followerHandle
        GetFollowersRequest getFollowersRequest = new GetFollowersRequest();
        getFollowersRequest.setUserhandle(author);
        getFollowersRequest.setLastFollower(null);      //We don't care about pagination so this is set to null

        FollowDAO followDAO = new FollowDAO();
        GetFollowersResponse getFollowersResponse = followDAO.getAllFollowers(getFollowersRequest);
        List<FollowInfo> followers = getFollowersResponse.getFollowers();       // List of all the followers of the author


        for (int i = 0; i < followers.size();) {
            List<FollowInfo> follows = new ArrayList<>();
            for (int j = 0; j < 25 && i < followers.size(); j++) {
                follows.add(followers.get(i));
                i++;
            }

            PostStatusRequest postStatus = new PostStatusRequest();
            postStatus.setFollowers(follows);
            postStatus.setStatus(request);

            String messageBody = gson.toJson(postStatus);

            String queueUrl = "https://sqs.us-west-2.amazonaws.com/386757273596/PostStatus";

            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(messageBody);

            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
            SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

            String msgId = send_msg_result.getMessageId();
        }

        return null;

    }
}
