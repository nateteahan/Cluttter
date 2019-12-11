package handler;

import DatabaseAccess.StatusDAO;
import Model.PostStatusRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;

public class StatusProcessor implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent event, Context context) {

        Gson gson = new Gson();
        PostStatusRequest request = new PostStatusRequest();

        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            request = gson.fromJson(msg.getBody(), PostStatusRequest.class);
        }


        StatusDAO statusDAO = new StatusDAO();
        String message = statusDAO.postToFeed(request, context);

        return null;

    }

}
