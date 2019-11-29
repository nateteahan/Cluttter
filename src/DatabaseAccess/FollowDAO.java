package DatabaseAccess;

import Model.FollowInfo;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
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

    public GetFollowersResponse getFollowers(GetFollowersRequest request) {
        GetFollowersResponse result;
        List<FollowInfo> followers = new ArrayList<>();

        Table table = dynamoDB.getTable(TableName);

        Index index = table.getIndex("followeeHandle-followerHandle-index");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("followeeHandle = :followee")
                .withValueMap(new ValueMap()
                        .withString(":followee", request.getUserhandle()));

        ItemCollection<QueryOutcome> items = index.query(spec);

        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            FollowInfo info = new FollowInfo(item.getString(FollowerHandleAttr));
            followers.add(info);
            /* FIXME --> I want to have the profile pic in the item as well.
            *   If that is too much work, add in the name of the follower AND follower in the Feed table */
        }

        if (followers.size() > 0) {
            result = new GetFollowersResponse(followers, null);
        }
        else {
            result = new GetFollowersResponse(null, "This user has no followers");
        }


        return result;
    }

    public GetFollowingResponse getFollowing(GetFollowingRequest request) {
        GetFollowingResponse result;
        List<FollowInfo> following = new ArrayList<>();

        Table table = dynamoDB.getTable(TableName);

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("followerHandle = :follower")
                .withValueMap(new ValueMap()
                    .withString(":follower", request.getUserhandle()));

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            FollowInfo info = new FollowInfo(item.getString(FolloweeHandleAttr));
            following.add(info);

            /* FIXME --> I want to have the profile pic in the item as well.
             *   If that is too much work, add in the name of the follower AND follower in the Feed table */
        }

        if (following.size() > 0) {
            result = new GetFollowingResponse(following, null);
        }
        else {
            result = new GetFollowingResponse(null, "This user isn't following anyone");
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
