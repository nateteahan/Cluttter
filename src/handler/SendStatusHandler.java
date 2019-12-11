package handler;

import DatabaseAccess.StatusDAO;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import request.SendStatusRequest;
import response.SendStatusResponse;

public class SendStatusHandler {
        public SendStatusResponse sendStatusHandler(SendStatusRequest request, Context context) {
            StatusDAO statusDAO = new StatusDAO();
            String message = statusDAO.postToStory(request);

            Gson gson = new Gson();
            String messageBody = gson.toJson(request);

            String queueUrl = "https://sqs.us-west-2.amazonaws.com/386757273596/GetFollowers";

            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(messageBody);

            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
            SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

            String msgId = send_msg_result.getMessageId();

            return new SendStatusResponse(message);
        }
}
