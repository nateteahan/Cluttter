package DatabaseAccess;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class StatusDAO {
    // CRUD operations
    private static final String FeedTableName = "Feed";
    private static final String StoryTableName = "Story";

    private static final String ProfilePicAttr = "profilePic";
    private static final String FirstNameAttr = "firstName";
    private static final String UserHandleAttr = "userHandle";
    private static final String TimeAttr = "time";
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

    public String postStatus(String profilePic, String firstName, String userHandle, String time, String status, String imageAttachment, String videoAttachment) {

        /* Add the status to the Feed table */
        Table feedTable = dynamoDB.getTable(FeedTableName);

        Item feedItem = new Item().withPrimaryKey(UserHandleAttr, userHandle)
                .withString(TimeAttr, time)
                .withString(FirstNameAttr, firstName)
                .withString(ProfilePicAttr, profilePic)
                .withString(StatusAttr, status)
                .withString(ImageAttr, imageAttachment)
                .withString(VideoAttr, videoAttachment);

        try {
            feedTable.putItem(feedItem);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Add the status to the Story table */
        Table storyTable = dynamoDB.getTable(StoryTableName);

        Item storyItem = new Item().withPrimaryKey(UserHandleAttr, userHandle)
                .withString(TimeAttr, time)
                .withString(FirstNameAttr, firstName)
                .withString(ProfilePicAttr, profilePic)
                .withString(StatusAttr, status)
                .withString(ImageAttr, imageAttachment)
                .withString(VideoAttr, videoAttachment);

        try {
            storyTable.putItem(storyItem);
        } catch (Exception f) {
            f.printStackTrace();
        }

        return "FIXME";
    }
}
