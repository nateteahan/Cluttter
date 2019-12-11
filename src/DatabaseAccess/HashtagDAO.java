package DatabaseAccess;

import Model.Status;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import request.GetHashtagsRequest;
import request.PostHashtagRequest;
import response.GetHashtagsResponse;
import response.PostHashtagResponse;

import java.util.*;

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

    public GetHashtagsResponse getHashtags(GetHashtagsRequest request, Context context, String lastKey) {
        LambdaLogger logger = context.getLogger();
        List<Status> statuses = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#H", HashtagAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":hashtag", new AttributeValue().withS(request.getHashtag()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#H = :hashtag")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withScanIndexForward(false)
                .withLimit(3);

        if (isNonEmptyString(lastKey)) {
            logger.log("UNPARSED LAST KEY: " + lastKey);
            StringBuilder sb = new StringBuilder(lastKey);
            sb.insert(2, "/");
            sb.insert(5, "/");
            sb.insert(8, " ");
            sb.insert(11, ":");
            sb.insert(14, ":");

            lastKey = sb.toString();
            logger.log("PARSED LAST KEY: " + lastKey);

            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(TimeAttr, new AttributeValue().withS(lastKey));
            startKey.put(HashtagAttr, new AttributeValue().withS(request.getHashtag()));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = client.query(queryRequest);
        List<Map<String, AttributeValue>> items1 = queryResult.getItems();
        if (items1 != null) {
            for (Map<String, AttributeValue> item : items1) {
                String userhandle = item.get(UserHandleAttr).getS();
                String profilePic = item.get(ProfilePicAttr).getS();
                String firstName = item.get(FirstNameAttr).getS();
                String status = item.get(StatusAttr).getS();
                String time = item.get(TimeAttr).getS();
                String imageAttachment = null;
                String videoAttachment = null;

                if (item.get(ImageAttr) != null) {
                    imageAttachment = item.get(ImageAttr).getS();
                }
                else if (item.get(VideoAttr) != null) {
                    videoAttachment = item.get(VideoAttr).getS();
                }

                Status newStatus = new Status(profilePic, firstName, userhandle, time, status, imageAttachment, videoAttachment);
                statuses.add(newStatus);
            }

            Map<String, AttributeValue> lastKey1 = queryResult.getLastEvaluatedKey();
            String newLastKey = null;
            if (lastKey1 != null) {
                newLastKey = lastKey1.get(TimeAttr).getS();
            }

            return new GetHashtagsResponse(statuses, null, newLastKey);
        }
        else {
            return new GetHashtagsResponse(null, "No posts with this hashtag", null);
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
