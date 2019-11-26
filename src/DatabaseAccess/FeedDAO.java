package DatabaseAccess;

import Model.FollowInfo;
import Model.Status;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import request.GetUserFeedRequest;
import response.GetUserFeedResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FeedDAO {
    // CRUD operations
    private static final String TableName = "Feed";

    private static final String UserHandleAttr = "userHandle";

    // DynamoDB client
    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    public GetUserFeedResponse getUserFeed(GetUserFeedRequest request) {
        List<Status> feed = new ArrayList<>();
        String message = null;
        Table table = dynamoDB.getTable(TableName);

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("userHandle = :u")
                .withValueMap(new ValueMap()
                    .withString(":u", request.getUserhandle()))
                .withScanIndexForward(false);

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();

            if (item.getString("imageAttachment") != null) {
                String imageAttachment = item.getString("imageAttachment");
                String time = item.getString("time");
                String author = item.getString("author");
                String firstName = item.getString("firstName");
                String profilePic = item.getString("profilePic");
                String status = item.getString("status");

                Status statusInfo = new Status(profilePic, firstName, author, time, status, imageAttachment, null);
                feed.add(statusInfo);

            }
            else if (item.getString("videoAttachment") != null) {
                String videoAttachment = item.getString("videoAttachment");
                String time = item.getString("time");
                String author = item.getString("author");
                String firstName = item.getString("firstName");
                String profilePic = item.getString("profilePic");
                String status = item.getString("status");

                Status statusInfo = new Status(profilePic, firstName, author, time, status, null, videoAttachment);
                feed.add(statusInfo);
            }
            else {
                String time = item.getString("time");
                String author = item.getString("author");
                String firstName = item.getString("firstName");
                String profilePic = item.getString("profilePic");
                String status = item.getString("status");

                Status statusInfo = new Status(profilePic, firstName, author, time, status, null, null);
                feed.add(statusInfo);
            }
        }

        if (feed.size() == 0) {
            feed = null;
            message = "No feed to show";
        }

        return new GetUserFeedResponse(feed, message);
    }
}
