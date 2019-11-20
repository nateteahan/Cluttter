package DatabaseAccess;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.lambda.runtime.Context;
import response.GetUserResponse;

public class UserDAO {
    // CRUD operations
    private static final String TableName = "User";

    private static final String UserHandleAttr = "userHandle";
    private static final String FirstNameAttr = "firstName";
    private static final String LastNameAttr = "lastName";
    private static final String ProfilePicAttr = "profilePic";

    // DynamoDB client
    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    /**
     * Creates a user and stores in the "User" table
     *
     * @param userHandle
     * @param firstName
     * @param lastName
     * @param profilePic
     */
    public void createUser(String userHandle, String firstName, String lastName, String profilePic) {
        Table table = dynamoDB.getTable(TableName);

        Item item = new Item().withPrimaryKey(UserHandleAttr, userHandle)
                                .withString(FirstNameAttr, firstName)
                                .withString(LastNameAttr, lastName)
                                .withString(ProfilePicAttr, profilePic);
        table.putItem(item);
    }

    /**
     * Gets a user from the "User" table
     *
     * @param userHandle
     */
    public GetUserResponse getUser(String userHandle, Context context) {
        Table table = dynamoDB.getTable("User");
        GetUserResponse response;

        try {
            context.getLogger().log("userHandle is: " + userHandle);
            Item item = table.getItem("userHandle", userHandle);

            String handle = item.get("userHandle").toString();
            String profilePic = item.get("profilePic").toString();
            String firstName = item.get("firstName").toString();
            String lastName = item.get("lastName").toString();
            String email = item.get("email").toString();

            response = new GetUserResponse(userHandle, firstName, lastName, email, profilePic, null);

            return response;
        } catch (Exception e) {
            System.out.println("Something went wrong with getUser function");
            e.printStackTrace();

            response = new GetUserResponse(null, null, null, null, null, "User not found!");
            return response;
        }
    }


    /**
     * Updates a user profile picture in the "User" table
     *
     * @param userHandle
     * @param newPicture
     */
    public String updateUser(String userHandle, String newPicture) {
        String message;
        Table table = dynamoDB.getTable(TableName);
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("userHandle", userHandle)
                                                            .withUpdateExpression("set profilePic=:p")
                                                            .withValueMap(new ValueMap().withString(":p", newPicture))
                                                            .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            System.out.println("Updating the user picture...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
            message = "Success";
        }
        catch (Exception e) {
            System.err.println("Unable to update " + userHandle + "'s profile picture");
            System.err.println(e.getMessage());
            message = "Failure";
        }

        return message;
    }
}
