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
import request.GetUserStoryRequest;
import response.GetUserFeedResponse;
import response.GetUserStoryResponse;

import java.util.*;

public class StoryDAO {
    // CRUD operations
    private static final String TableName = "Story";

    private static final String UserHandleAttr = "userHandle";
    private static final String TimeAttr = "time";

    // DynamoDB client
    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    public GetUserStoryResponse getUserStory(GetUserStoryRequest request) {
        List<Status> story = new ArrayList<>();
        String message = null;
        Table table = dynamoDB.getTable(TableName);

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("userHandle = :u")
                .withValueMap(new ValueMap()
                    .withString(":u", request.getUserHandle()))
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
                story.add(statusInfo);

            }
            else if (item.getString("videoAttachment") != null) {
                String videoAttachment = item.getString("videoAttachment");
                String time = item.getString("time");
                String author = item.getString("author");
                String firstName = item.getString("firstName");
                String profilePic = item.getString("profilePic");
                String status = item.getString("status");

                Status statusInfo = new Status(profilePic, firstName, author, time, status, null, videoAttachment);
                story.add(statusInfo);
            }
            else {
                String time = item.getString("time");
                String author = item.getString("author");
                String firstName = item.getString("firstName");
                String profilePic = item.getString("profilePic");
                String status = item.getString("status");

                Status statusInfo = new Status(profilePic, firstName, author, time, status, null, null);
                story.add(statusInfo);
            }
        }

        if (story.size() == 0) {
            story = null;
            message = "No story to show";
        }

        return new GetUserStoryResponse(story, message, null);
    }

    public GetUserStoryResponse STORY(GetUserStoryRequest request, String lastKey, Context context) {
        LambdaLogger logger = context.getLogger();
        List<Status> feed = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#u", UserHandleAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":handle", new AttributeValue().withS(request.getUserHandle()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#u = :handle")
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
            startKey.put(UserHandleAttr, new AttributeValue().withS(request.getUserHandle()));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = client.query(queryRequest);
        List<Map<String, AttributeValue>> items1 = queryResult.getItems();
        if (items1 != null) {
            for (Map<String, AttributeValue> item : items1) {
                if (item.get("imageAttachment") != null) {
                    String imageAttachment = item.get("imageAttachment").getS();
                    String author = item.get("userHandle").getS();
                    String time = item.get("time").getS();
                    String firstName = item.get("firstName").getS();
                    String profilePic = item.get("profilePic").getS();
                    String status = item.get("status").getS();

                    Status statusInfo = new Status(profilePic, firstName, author, time, status, imageAttachment, null);
                    feed.add(statusInfo);
                }
                else if (item.get("videoAttachment") != null) {
                    String videoAttachment = item.get("videoAttachment").getS();
                    String time = item.get("time").getS();
                    String author = item.get("userHandle").getS();
                    String firstName = item.get("firstName").getS();
                    String profilePic = item.get("profilePic").getS();
                    String status = item.get("status").getS();

                    Status statusInfo = new Status(profilePic, firstName, author, time, status, null, videoAttachment);
                    feed.add(statusInfo);
                }
                else {
                    String time = item.get("time").getS();
                    String author = item.get("userHandle").getS();
                    String firstName = item.get("firstName").getS();
                    String profilePic = item.get("profilePic").getS();
                    String status = item.get("status").getS();

                    Status statusInfo = new Status(profilePic, firstName, author, time, status, null, null);
                    feed.add(statusInfo);
                }
            }

            Map<String, AttributeValue> lastKey1 = queryResult.getLastEvaluatedKey();
            String newLastKey = null;
            if (lastKey1 != null) {
//            result.setLastKey(lastKey.get(LocationAttr).getS());
                newLastKey = lastKey1.get(TimeAttr).getS();
            }

            return new GetUserStoryResponse(feed, null, newLastKey);
        }
        else {
            return new GetUserStoryResponse(null, "No feed to show", null);
        }
    }
}
