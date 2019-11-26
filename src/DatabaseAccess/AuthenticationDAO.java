package DatabaseAccess;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class AuthenticationDAO {
    // CRUD operations
    private static final String TableName = "Authentication";

    private static final String UserHandleAttr = "userHandle";
    private static final String AuthTokenAttr = "authToken";


    // DynamoDB client
    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    public void authenticateUser(String userHandle, String authToken) {
        Table table = dynamoDB.getTable(TableName);

        Item item = new Item()
                .withPrimaryKey(UserHandleAttr, userHandle)
                .withString(AuthTokenAttr, authToken);

        try {
            table.putItem(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkAuthentication(String userHandle, String authToken) {

    }
}
