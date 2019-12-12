package handler;

import DatabaseAccess.StatusDAO;
import SQS.SQSGetFollowers;
import SQS.ISQSQueue;
import com.amazonaws.services.lambda.runtime.Context;
import request.SendStatusRequest;
import response.SendStatusResponse;

public class SendStatusHandler {
        public SendStatusResponse sendStatusHandler(SendStatusRequest request, Context context) {
            StatusDAO statusDAO = new StatusDAO();
            String message = statusDAO.postToStory(request);

            ISQSQueue queue = new SQSGetFollowers();
            queue.GetFollowers(request, "https://sqs.us-west-2.amazonaws.com/386757273596/GetFollowers");
//            Gson gson = new Gson();
//            String messageBody = gson.toJson(request);
//
//            String queueUrl = "https://sqs.us-west-2.amazonaws.com/386757273596/GetFollowers";
//
//            SendMessageRequest send_msg_request = new SendMessageRequest()
//                    .withQueueUrl(queueUrl)
//                    .withMessageBody(messageBody);
//
//            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
//            SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);
//
//            String msgId = send_msg_result.getMessageId();

            return new SendStatusResponse(message);
        }
}
