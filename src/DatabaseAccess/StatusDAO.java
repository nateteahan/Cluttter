package DatabaseAccess;

import Model.FollowInfo;
import Model.PostStatusRequest;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.services.lambda.runtime.Context;
import request.SendStatusRequest;

import java.util.List;
import java.util.Map;

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

    public String postToStory(SendStatusRequest request) {
        String message = "Successfully posted status!";
        boolean picture = false;
        boolean video = false;

        // Don't ask. Trust the process.
        // Had to add both conditions so that it worked with lambda and api gateway
        // Api gateway mapping template will replace imageAttachment/videoAttachment with ""
        // Lambda allows for imageAttachment/videoAttachment to be null.
        // This is the workaround both tests
        if ((request.getImageAttachment() != null && !request.getImageAttachment().equals("")) && (request.getVideoAttachment() != null && !request.getVideoAttachment().equals(""))) {
            return "Only attach a picture OR a video please";
        }
        if (request.getVideoAttachment() != null && !request.getVideoAttachment().equals("")) {
            video = true;
        }
        else if (request.getImageAttachment() != null && !request.getImageAttachment().equals("")) {
            picture = true;
        }

        /* Add the status to the Story table */
        Table storyTable = dynamoDB.getTable(StoryTableName);
        try {
            // If both video and photo attachments are null, leave them out of the scheme
            if (!picture && !video) {
                Item storyItem = new Item().withPrimaryKey(UserHandleAttr, request.getUserhandle())
                        .withString(TimeAttr, request.getTime())
                        .withString(FirstNameAttr, request.getFirstName())
                        .withString(ProfilePicAttr, request.getProfilePic())
                        .withString(StatusAttr, request.getStatus());

                storyTable.putItem(storyItem);
            }
            else if (picture) {
                Item storyItem = new Item().withPrimaryKey(UserHandleAttr, request.getUserhandle())
                        .withString(TimeAttr, request.getTime())
                        .withString(FirstNameAttr, request.getFirstName())
                        .withString(ProfilePicAttr, request.getProfilePic())
                        .withString(StatusAttr, request.getStatus())
                        .withString(ImageAttr, request.getImageAttachment());

                storyTable.putItem(storyItem);
            }
            else {
                Item storyItem = new Item().withPrimaryKey(UserHandleAttr, request.getUserhandle())
                        .withString(TimeAttr, request.getTime())
                        .withString(FirstNameAttr, request.getFirstName())
                        .withString(ProfilePicAttr, request.getProfilePic())
                        .withString(StatusAttr, request.getStatus())
                        .withString(VideoAttr, request.getVideoAttachment());

                storyTable.putItem(storyItem);
            }
        } catch (Exception f) {
            message = "Could not post status to story";
            f.printStackTrace();
        }

        return message;
    }

    public String postToFeed(PostStatusRequest request, Context context) {
        String message = "Successfully posted status to Feed Table";
        boolean picture = false;
        boolean video = false;
        List<FollowInfo> followers = request.getFollowers();
        // Don't ask. Trust the process.
        // Had to add both conditions so that it worked with lambda and api gateway
        // Api gateway mapping template will replace imageAttachment/videoAttachment with ""
        // Lambda allows for imageAttachment/videoAttachment to be null.
        // This is the workaround both tests
        if (followers.size() > 0) {
//        logger.log("Number of followers is " + followers.size());
            int attachments = determineAttachments(request.getStatus());

            if (attachments == -1) {
                return "Only attach a photo OR a gif";
            }

            if (attachments == 0) {
                TableWriteItems tableWriter = new TableWriteItems(FeedTableName);
                for (int i = 0; i < followers.size();) {
                    for (int j = 0; j < 25 && i < followers.size(); j++) {
                        Item feedItem = new Item().withPrimaryKey(UserHandleAttr, followers.get(i).getUserHandle())
                                .withString(TimeAttr, request.getStatus().getTime())
                                .withString(FirstNameAttr, request.getStatus().getFirstName())
                                .withString(ProfilePicAttr, request.getStatus().getProfilePic())
                                .withString(StatusAttr, request.getStatus().getStatus())
                                .withString(AuthorAttr, request.getStatus().getUserhandle());
                        i++;
                        tableWriter.addItemToPut(feedItem);
                    }

                    try {
                        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriter);

                        do {
                            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();

                            if (unprocessedItems.size() > 0) {
                                outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
                            }
                        } while (outcome.getUnprocessedItems().size() > 0);
                    } catch (Exception e) {
                        message = "Something went wrong with no attachments";
                    }
                }
            }

            else if (attachments == 1) {
                TableWriteItems tableWriter = new TableWriteItems(FeedTableName);
                for (int i = 0; i < followers.size();) {
                    for (int j = 0; j < 25 && i < followers.size(); j++) {
                        Item feedItem = new Item().withPrimaryKey(UserHandleAttr, followers.get(i).getUserHandle())
                                .withString(TimeAttr, request.getStatus().getTime())
                                .withString(FirstNameAttr, request.getStatus().getFirstName())
                                .withString(ProfilePicAttr, request.getStatus().getProfilePic())
                                .withString(StatusAttr, request.getStatus().getStatus())
                                .withString(AuthorAttr, request.getStatus().getUserhandle())
                                .withString(VideoAttr, request.getStatus().getVideoAttachment());
                        i++;
                        tableWriter.addItemToPut(feedItem);
                    }

                    try {
                        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriter);

                        do {
                            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();

                            if (unprocessedItems.size() > 0) {
                                outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
                            }
                        } while (outcome.getUnprocessedItems().size() > 0);
                    } catch (Exception e) {
                        message = "Something went wrong with no attachments";
                    }
                }
            }

            else if (attachments == 2) {
                TableWriteItems tableWriter = new TableWriteItems(FeedTableName);
                for (int i = 0; i < followers.size();) {
                    for (int j = 0; j < 25 && i < followers.size(); j++) {
                        Item feedItem = new Item().withPrimaryKey(UserHandleAttr, followers.get(i).getUserHandle())
                                .withString(TimeAttr, request.getStatus().getTime())
                                .withString(FirstNameAttr, request.getStatus().getFirstName())
                                .withString(ProfilePicAttr, request.getStatus().getProfilePic())
                                .withString(StatusAttr, request.getStatus().getStatus())
                                .withString(AuthorAttr, request.getStatus().getUserhandle())
                                .withString(ImageAttr, request.getStatus().getImageAttachment());
                        i++;
                        tableWriter.addItemToPut(feedItem);
                    }

                    try {
                        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriter);

                        do {
                            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();

                            if (unprocessedItems.size() > 0) {
                                outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
                            }
                        } while (outcome.getUnprocessedItems().size() > 0);
                    } catch (Exception e) {
                        message = "Something went wrong with no attachments";
                    }
                }
            }

            return message;
        }
        else {
            // No user's whose feed to post on
            return null;
        }
    }

    private int determineAttachments(SendStatusRequest request) {
        if ((request.getImageAttachment() != null && !request.getImageAttachment().equals("")) && (request.getVideoAttachment() != null && !request.getVideoAttachment().equals(""))) {
            return -1;
        }
        else if (request.getVideoAttachment() != null && !request.getVideoAttachment().equals("")) {
            return 1;
        }
        else if (request.getImageAttachment() != null && !request.getImageAttachment().equals("")) {
            return 2;
        }

        // No attachments
        return 0;
    }
}