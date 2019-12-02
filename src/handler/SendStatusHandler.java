package handler;

import DatabaseAccess.FollowDAO;
import DatabaseAccess.StatusDAO;
import Model.FollowInfo;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import request.GetFollowersRequest;
import request.SendStatusRequest;
import response.GetFollowersResponse;
import response.SendStatusResponse;

import java.util.ArrayList;
import java.util.List;

public class SendStatusHandler {
        public SendStatusResponse sendStatusHandler(SendStatusRequest request, Context context) {
            LambdaLogger logger = context.getLogger();
            /* Need to get the request.gerUserHandle's followers and post to their feed.
            * Call the FollowsDAO, get the list of followers
            * Parse through the data and pass in List<String> followers to the StatusDAo*/

            FollowDAO followDAO = new FollowDAO();
            GetFollowersRequest followersRequest = new GetFollowersRequest();
            // Set the followersRequest's handle to the handle of the person posting the status
            followersRequest.setUserhandle(request.getUserhandle());
            GetFollowersResponse followersResponse = followDAO.getFollowers(followersRequest, context, "GETALL");
//            GetFollowersResponse followersResponse1 = followDAO.getAllFollowers(followersRequest);
            List<FollowInfo> followInfo = followersResponse.getFollowers();
            List<String> followers = new ArrayList<>();

            logger.log("\n\n");
            logger.log("SendStatusHandler imageAttachment = " + request.getImageAttachment());
            logger.log("\n");
            logger.log("SendStatusHandler videoAttachment = " + request.getVideoAttachment());
            logger.log("\n\n");

            if (followInfo != null && followInfo.size() > 0) {
                for (FollowInfo info : followInfo) {
                    followers.add(info.getUserHandle());
                }
            }

            StatusDAO statusDAO = new StatusDAO();
            String message = statusDAO.postStatus(request.getProfilePic(), request.getFirstName(), request.getUserhandle(),
                                                    request.getTime(), request.getStatus(), request.getImageAttachment(), request.getVideoAttachment(), followers, context);

            return new SendStatusResponse(message);
        }
}
