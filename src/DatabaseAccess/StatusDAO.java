package DatabaseAccess;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.List;

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
    private static final String AuthorAttr = "author";


    // DynamoDB client
    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    public String postStatus(String profilePic, String firstName, String userHandle, String time, String status, String imageAttachment, String videoAttachment, List<String> followers, Context context) {
        String message = "Successfully posted status!";
        LambdaLogger logger = context.getLogger();
        boolean picture = false;
        boolean video = false;

        logger.log("\n");
        logger.log("StatusDAO: imageAttachement = " + imageAttachment + "\n");
        logger.log("StatusDAO videoAttachment = " + videoAttachment + "\n");
        logger.log("\n");

        // Don't ask. Trust the process.
        // Had to add both conditions so that it worked with lambda and api gateway
        // Api gateway mapping template will replace imageAttachment/videoAttachment with ""
        // Lambda allows for imageAttachment/videoAttachment to be null.
        // This is the workaround both tests
        if ((imageAttachment != null && !imageAttachment.equals("")) && (videoAttachment != null && !videoAttachment.equals(""))) {
            return "Only attach a picture OR a video please";
        }
        if (videoAttachment != null && !videoAttachment.equals("")) {
            video = true;
        }
        else if (imageAttachment != null && !imageAttachment.equals("")) {
            picture = true;
        }
        // If the user has followers, post the status to the feed table
        if (followers != null && followers.size() > 0) {
            /* Add the status to the Feed table */
            Table feedTable = dynamoDB.getTable(FeedTableName);

            try {
                // For every follower, insert the status into their feed
                // If both video and photo attachments are null, leave them out of the scheme
                if (!picture && !video) {
                    logger.log("(FEED) No image, no video\n");
                    for (String follower : followers) {
                        Item feedItem = new Item().withPrimaryKey(UserHandleAttr, follower)
                                .withString(TimeAttr, time)
                                .withString(FirstNameAttr, firstName)
                                .withString(ProfilePicAttr, profilePic)
                                .withString(StatusAttr, status)
                                .withString(AuthorAttr, userHandle);

                        feedTable.putItem(feedItem);
                    }
                }
                // Photo is attached to the status, video is not
                else if (picture) {
                    logger.log("(FEED) Image attached\n");
                    for (String follower : followers) {
                        Item feedItem = new Item().withPrimaryKey(UserHandleAttr, follower)
                                .withString(TimeAttr, time)
                                .withString(FirstNameAttr, firstName)
                                .withString(ProfilePicAttr, profilePic)
                                .withString(StatusAttr, status)
                                .withString(AuthorAttr, userHandle)
                                .withString(ImageAttr, imageAttachment);

                        feedTable.putItem(feedItem);
                    }
                }
                // Video is attached to the status, photo is not
                else {
                    logger.log("(FEED) Video attached\n");
                    for (String follower : followers) {
                        Item feedItem = new Item().withPrimaryKey(UserHandleAttr, follower)
                                .withString(TimeAttr, time)
                                .withString(FirstNameAttr, firstName)
                                .withString(ProfilePicAttr, profilePic)
                                .withString(StatusAttr, status)
                                .withString(AuthorAttr, userHandle)
                                .withString(VideoAttr, videoAttachment);

                        feedTable.putItem(feedItem);
                    }
                }
            } catch (Exception e) {
                message = "Could not post status to feed.";
                e.printStackTrace();
            }
        }

        /* Add the status to the Story table */
        Table storyTable = dynamoDB.getTable(StoryTableName);
        try {
            // If both video and photo attachments are null, leave them out of the scheme
            if (!picture && !video) {
                logger.log("(STORY) No image, no video\n\n");
                Item storyItem = new Item().withPrimaryKey(UserHandleAttr, userHandle)
                            .withString(TimeAttr, time)
                            .withString(FirstNameAttr, firstName)
                            .withString(ProfilePicAttr, profilePic)
                            .withString(StatusAttr, status);

                    storyTable.putItem(storyItem);
            }
            else if (picture) {
                logger.log("(STORY) Image attached\n\n");
                Item storyItem = new Item().withPrimaryKey(UserHandleAttr, userHandle)
                            .withString(TimeAttr, time)
                            .withString(FirstNameAttr, firstName)
                            .withString(ProfilePicAttr, profilePic)
                            .withString(StatusAttr, status)
                            .withString(ImageAttr, imageAttachment);

                    storyTable.putItem(storyItem);
            }
            else {
                logger.log("(STORY) Video attached\n\n");
                Item storyItem = new Item().withPrimaryKey(UserHandleAttr, userHandle)
                            .withString(TimeAttr, time)
                            .withString(FirstNameAttr, firstName)
                            .withString(ProfilePicAttr, profilePic)
                            .withString(StatusAttr, status)
                            .withString(VideoAttr, videoAttachment);

                    storyTable.putItem(storyItem);
            }
        } catch (Exception f) {
            message = "Could not post status to story";
            f.printStackTrace();
        }

        return message;
    }
}
