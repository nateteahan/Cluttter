package DatabaseAccess;

import Model.FollowInfo;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import request.*;
import response.GetFollowersResponse;
import response.GetFollowingResponse;
import response.IsFollowingResponse;

import java.util.*;

public class FollowDAO {
    // CRUD operations
    private static final String TableName = "Follows";

    private static final String FollowerHandleAttr = "followerHandle";
    private static final String FolloweeHandleAttr = "followeeHandle";

    // DynamoDB client
    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    public String followUser(FollowUserRequest request) {
        Table table = dynamoDB.getTable(TableName);
        Item item = new Item().withPrimaryKey(FollowerHandleAttr, request.getFollowerHandle(), FolloweeHandleAttr, request.getFolloweeHandle());

        try {
            table.putItem(item);
            return "Successfully followed @" + request.getFolloweeHandle();
        } catch (Exception e) {
            e.printStackTrace();
            return "Follow unsuccessful";
        }
    }

    public String unfollowUser(UnfollowUserRequest request) {
        Table table = dynamoDB.getTable(TableName);
        DeleteItemSpec item = new DeleteItemSpec().withPrimaryKey(FollowerHandleAttr, request.getFollowerHandle(),
                FolloweeHandleAttr, request.getFolloweeHandle());

        try {
            table.deleteItem(item);
            return "You have now unfollowed @" + request.getFolloweeHandle();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unfollow unsuccessful";
        }
    }

    public GetFollowersResponse getFollowers(GetFollowersRequest request, Context context, String lastKey) {
        LambdaLogger logger = context.getLogger();
        GetFollowersResponse result;
        List<FollowInfo> followers = new ArrayList<>();

        Table table = dynamoDB.getTable(TableName);

        Index index = table.getIndex("followeeHandle-followerHandle-index");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("followeeHandle = :followee")
                .withValueMap(new ValueMap()
                        .withString(":followee", request.getUserhandle()))
                        .withMaxPageSize(3);

//        if (isNonEmptyString(lastKey) && !lastKey.equals("GETALL")) {
//            spec = spec.withExclusiveStartKey(new KeyAttribute(FolloweeHandleAttr, lastKey));
//        }

        ItemCollection<QueryOutcome> items = index.query(spec);


        //REFERENCE 2
            Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#fol", FolloweeHandleAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":followee", new AttributeValue().withS(request.getUserhandle()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#fol = :followee")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withIndexName(index.getIndexName())
                .withLimit(3);

        if (isNonEmptyString(lastKey)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(FollowerHandleAttr, new AttributeValue().withS(lastKey));
            startKey.put(FolloweeHandleAttr, new AttributeValue().withS(request.getUserhandle()));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = client.query(queryRequest);
        List<Map<String, AttributeValue>> items1 = queryResult.getItems();
        if (items1 != null) {
            for (Map<String, AttributeValue> item : items1){
                String userHandle = item.get(FollowerHandleAttr).getS();
                FollowInfo info2 = new FollowInfo(userHandle);
                followers.add(info2);
            }
        }

        Map<String, AttributeValue> lastKey1 = queryResult.getLastEvaluatedKey();
        String newLastKey = null;
        if (lastKey1 != null) {
//            result.setLastKey(lastKey.get(LocationAttr).getS());
            newLastKey = lastKey1.get(FollowerHandleAttr).getS();
        }
        /* REFERENCE */
//        Map<String, String> attrNames = new HashMap<String, String>();
//        attrNames.put("#vis", VisitorAttr);
//
//        Map<String, AttributeValue> attrValues = new HashMap<>();
//        attrValues.put(":visitor", new AttributeValue().withS(visitor));
//
//        QueryRequest queryRequest = new QueryRequest()
//                .withTableName(TableName)
//                .withKeyConditionExpression("#vis = :visitor")
//                .withExpressionAttributeNames(attrNames)
//                .withExpressionAttributeValues(attrValues)
//                .withLimit(pageSize);
//
//        if (isNonEmptyString(lastLocation)) {
//            Map<String, AttributeValue> startKey = new HashMap<>();
//            startKey.put(VisitorAttr, new AttributeValue().withS(visitor));
//            startKey.put(LocationAttr, new AttributeValue().withS(lastLocation));

//        /* */
//        int startPage = 0;
//        for (Page<Item, QueryOutcome> page : items.pages()) {
//            logger.log("Page: " + ++startPage);
//
//            Iterator<Item> iterator = page.iterator();
//            while (iterator.hasNext()) {
//                Item item = iterator.next();
//                logger.log(item.getString(FollowerHandleAttr) + "\n");
//                FollowInfo info = new FollowInfo(item.getString(FollowerHandleAttr));
//                followers.add(info);
//            }
//        }

//        Iterator<Item> iter = items.iterator();
//        while (iter.hasNext()) {
//            Item item = iter.next();
//            FollowInfo info = new FollowInfo(item.getString(FollowerHandleAttr));
//            followers.add(info);
//            /* FIXME --> I want to have the profile pic in the item as well.
//             *   If that is too much work, add in the name of the follower AND follower in the Feed table */
//        }

        if (followers.size() > 0) {
            result = new GetFollowersResponse(followers, null, newLastKey);
        }
        else {
            result = new GetFollowersResponse(null, "This user has no followers", null);
        }


        return result;
    }

    public GetFollowingResponse getFollowing(GetFollowingRequest request, Context context, String lastKey) {
        GetFollowingResponse result;
        List<FollowInfo> following = new ArrayList<>();

        Table table = dynamoDB.getTable(TableName);
//
//        QuerySpec spec = new QuerySpec()
//                .withKeyConditionExpression("followerHandle = :follower")
//                .withValueMap(new ValueMap()
//                    .withString(":follower", request.getUserhandle()));
//
//        ItemCollection<QueryOutcome> items = table.query(spec);

        //REFERENCE 2
        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#fol", FollowerHandleAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":follower", new AttributeValue().withS(request.getUserhandle()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TableName)
                .withKeyConditionExpression("#fol = :follower")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(3);

        if (isNonEmptyString(lastKey)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(FolloweeHandleAttr, new AttributeValue().withS(lastKey));
            startKey.put(FollowerHandleAttr, new AttributeValue().withS(request.getUserhandle()));

            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = client.query(queryRequest);
        List<Map<String, AttributeValue>> items1 = queryResult.getItems();
        if (items1 != null) {
            for (Map<String, AttributeValue> item : items1){
                String userHandle = item.get(FolloweeHandleAttr).getS();
                FollowInfo info2 = new FollowInfo(userHandle);
                following.add(info2);
            }
        }

        Map<String, AttributeValue> lastKey1 = queryResult.getLastEvaluatedKey();
        String newLastKey = null;
        if (lastKey1 != null) {
//            result.setLastKey(lastKey.get(LocationAttr).getS());
            newLastKey = lastKey1.get(FolloweeHandleAttr).getS();
        }
//        Iterator<Item> iter = items.iterator();
//        while (iter.hasNext()) {
//            Item item = iter.next();
//            FollowInfo info = new FollowInfo(item.getString(FolloweeHandleAttr));
//            following.add(info);
//
//            /* FIXME --> I want to have the profile pic in the item as well.
//             *   If that is too much work, add in the name of the follower AND follower in the Feed table */
//        }

        if (following.size() > 0) {
            result = new GetFollowingResponse(following, null, newLastKey);
        }
        else {
            result = new GetFollowingResponse(null, "This user isn't following anyone", null);
        }

        return result;
    }

    public IsFollowingResponse isFollowing(IsFollowingRequest request) {
        Table table = dynamoDB.getTable(TableName);

        try {
            QuerySpec spec = new QuerySpec()
                    .withKeyConditionExpression("followerHandle = :f and followeeHandle = :g")
                    .withValueMap(new ValueMap()
                            .withString(":f", request.getFollowerHandle())
                            .withString(":g", request.getFolloweeHandle()));

            ItemCollection<QueryOutcome> items = table.query(spec);

            Iterator<Item> iter = items.iterator();
            if (iter.hasNext()) {
                // Is followed
                return new IsFollowingResponse("True");
            }
            else {
                // Is not followed
                return new IsFollowingResponse("False");
            }
        } catch (Exception e) {
            return new IsFollowingResponse(e.toString());
        }
    }
}
