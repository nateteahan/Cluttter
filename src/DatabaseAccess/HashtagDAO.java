package DatabaseAccess;

import Model.Status;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import request.GetHashtagsRequest;
import request.PostHashtagRequest;
import response.GetHashtagsResponse;
import response.PostHashtagResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HashtagDAO {
    // CRUD operations
    private static final String TableName = "Hashtag";

    private static final String HashtagAttr = "hashtag";
    private static final String TimeAttr = "time";
    private static final String ProfilePicAttr = "profilePic";
    private static final String FirstNameAttr = "firstName";
    private static final String UserHandleAttr = "userHandle";
    private static final String StatusAttr = "status";
    private static final String ImageAttr = "imageAttachment";
    private static final String VideoAttr = "videoAttachment";

    // DynamoDB client
    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    public GetHashtagsResponse getHashtags(GetHashtagsRequest request) {
        List<Status> statuses = new ArrayList<>();
        String message;

        Table table = dynamoDB.getTable(TableName);

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("hashtag = :t")
                .withValueMap(new ValueMap()
                        .withString(":t", request.getHashtag()))
                .withScanIndexForward(false);

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            String userhandle = item.getString(UserHandleAttr);
            String profilePic = item.getString(ProfilePicAttr);
            String firstName = item.getString(FirstNameAttr);
            String status = item.getString(StatusAttr);
            String time = item.getString(TimeAttr);
            String imageAttachment = item.getString(ImageAttr);
            String videoAttachment = item.getString(VideoAttr);

            Status newStatus = new Status(profilePic, firstName, userhandle, time, status, imageAttachment, videoAttachment);
            statuses.add(newStatus);
        }

        if (statuses.size() > 0) {
            return new GetHashtagsResponse(statuses, null);
        }
        else {
            return new GetHashtagsResponse(null, "No posts with this hashtag");
        }
    }

    public PostHashtagResponse postHashtag(PostHashtagRequest request) {
        Table table = dynamoDB.getTable(TableName);

        try {
            if (request.getImageAttachment() != null) {
                Item item = new Item()
                        .withPrimaryKey(HashtagAttr, request.getHashtag())
                        .withString(TimeAttr, request.getTime())
                        .withString(ProfilePicAttr, request.getProfilePic())
                        .withString(FirstNameAttr, request.getFirstName())
                        .withString(UserHandleAttr, request.getUserHandle())
                        .withString(StatusAttr, request.getStatus())
                        .withString(ImageAttr, request.getImageAttachment());

                table.putItem(item);
            }
            else if (request.getVideoAttachment() != null) {
                Item item = new Item()
                        .withPrimaryKey(HashtagAttr, request.getHashtag())
                        .withString(TimeAttr, request.getTime())
                        .withString(ProfilePicAttr, request.getProfilePic())
                        .withString(FirstNameAttr, request.getFirstName())
                        .withString(UserHandleAttr, request.getUserHandle())
                        .withString(StatusAttr, request.getStatus())
                        .withString(VideoAttr, request.getVideoAttachment());

                table.putItem(item);
            }
            else {
                Item item = new Item()
                        .withPrimaryKey(HashtagAttr, request.getHashtag())
                        .withString(TimeAttr, request.getTime())
                        .withString(ProfilePicAttr, request.getProfilePic())
                        .withString(FirstNameAttr, request.getFirstName())
                        .withString(UserHandleAttr, request.getUserHandle())
                        .withString(StatusAttr, request.getStatus());

                table.putItem(item);
            }

            return new PostHashtagResponse("Posted to Hashtag table");
        } catch (Exception e) {
            return new PostHashtagResponse(e.toString());
        }
    }
}
