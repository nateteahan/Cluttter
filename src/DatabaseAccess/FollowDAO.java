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
    private static final String FollowerNameAttr = "followerName";
    private static final String FolloweeNameAttr = "followeeName";

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
//                    .withString(FollowerNameAttr, request.getFollowerName())
//                    .withString(FolloweeNameAttr, request.getFolloweeName());

        table.putItem(item);

        return "Successfully followed user";
    }

    public String unfollowUser(UnfollowUserRequest request) {
        Table table = dynamoDB.getTable(TableName);
        DeleteItemSpec item = new DeleteItemSpec().withPrimaryKey(FollowerHandleAttr, request.getFollowerHandle(),
                                                                  FolloweeHandleAttr, request.getFolloweeHandle());

        String message;
        try {
            table.deleteItem(item);
            message = "You have now unfollowed " + request.getFolloweeHandle();
            return message;
        } catch (Exception e) {
            message = "Unfollow unsuccessful";
            e.printStackTrace();
            return message;
        }
    }
}
