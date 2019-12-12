package SQS;

import Model.PostStatusRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import request.SendStatusRequest;

public class SQSGetFollowers implements ISQSQueue {
    @Override
    public void GetFollowers(SendStatusRequest request, String QueueURL) {
        Gson gson = new Gson();
        String messageBody = gson.toJson(request);

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(QueueURL)
                .withMessageBody(messageBody);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

        String msgId = send_msg_result.getMessageId();
    }

    @Override
    public void PostStatus(PostStatusRequest request, String QueueURL) {
        //Blank
    }
}
