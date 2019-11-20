package DatabaseAccess;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import request.FollowUserRequest;
import request.UnfollowUserRequest;

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
            return "Successfully followed @" +request.getFolloweeHandle();
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
}
