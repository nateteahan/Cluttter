package DatabaseAccess;

import Model.FollowInfo;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.xspec.B;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import response.GetUserResponse;
import response.SignInResponse;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.UUID;

public class UserDAO {
    // CRUD operations
    private static final String TableName = "User";

    private static final String FirstNameAttr = "firstName";
    private static final String LastNameAttr = "lastName";
    private static final String EmailAttr = "email";
    private static final String UserHandleAttr = "userHandle";
    private static final String PasswordAttr = "password";
    private static final String ProfilePicAttr = "profilePic";
    private static final String SaltAttr = "salt";
    private static final String Bucket = "cluttter";

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
    public String createUser(String firstName, String lastName, String email, String userHandle, String password, String profilePic, Context context) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Table table = dynamoDB.getTable(TableName);
        LambdaLogger logger = context.getLogger();

        String salt = UUID.randomUUID().toString();
        logger.log("salt = " + salt + "\n");
        logger.log("password = " + password + "\n");
        password = hash(password, salt);

        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();

        byte[] decodedPicture = Base64.getDecoder().decode(profilePic);
        InputStream inputStream = new ByteArrayInputStream(decodedPicture);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(decodedPicture.length);

        String url = null;
        try {
            s3.putObject(new PutObjectRequest(Bucket, userHandle, inputStream, metadata));
            url = s3.getUrl(Bucket, userHandle).toExternalForm();

            Item item = new Item().withPrimaryKey(UserHandleAttr, userHandle)
                    .withString(FirstNameAttr, firstName)
                    .withString(LastNameAttr, lastName)
                    .withString(EmailAttr, email)
                    .withString(PasswordAttr, password)
                    .withString(ProfilePicAttr, url)
                    .withString(SaltAttr, salt);

            table.putItem(item);
            return url;
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    /**
     * Gets a user from the "User" table
     *
     * @param userHandle
     */
    public GetUserResponse getUserInfo(String userHandle, Context context) {
        Table table = dynamoDB.getTable("User");
        GetUserResponse response;

        try {
            Item item = table.getItem("userHandle", userHandle);

            String handle = item.get("userHandle").toString();
            String profilePic = item.get("profilePic").toString();
            String firstName = item.get("firstName").toString();
            String lastName = item.get("lastName").toString();
            String email = item.get("email").toString();

            response = new GetUserResponse(userHandle, firstName, lastName, email, profilePic, null);

            return response;
        } catch (Exception e) {
            System.out.println("User doesnt' exist");
            e.printStackTrace();

            response = new GetUserResponse(null, null, null, null, null, "User not found!");
            return response;
        }
    }

    /**
     * Signs a user in
     *
     * @param userHandle is the handle of user trying to sign in
     * @param userPassword is the password without the salt
     */
    public SignInResponse signInUser(String userHandle, String userPassword) {
        Table table = dynamoDB.getTable(TableName);

        // Query the table to see if the userHandle already exists
        try {
            GetItemSpec itemSpec = new GetItemSpec()
                    .withPrimaryKey(UserHandleAttr, userHandle);

            // Get the Salt from the table to be able to compare passwords
            Item item = table.getItem(itemSpec);
            String salt = item.getString(SaltAttr);
            String correctPassword = item.getString(PasswordAttr);

            userPassword = hash(userPassword, salt);
            if (userPassword.equals(correctPassword)) {
                //Correct sign in attempt
                // Generate authToken
                String authToken = UUID.randomUUID().toString();

                return new SignInResponse(null, userHandle, authToken);
            }
            else {
                // Invalid userHandle or password.
                return new SignInResponse("Invalid", null,null);
            }

        } catch (NullPointerException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new SignInResponse("Invalid", null,null);

        }
    }

    /**
     * Updates a user profile picture in the "User" table
     *
     * @param userHandle is name of user
     * @param newPicture uri of the new picture to be uploaded
     */
    public String updateUser(String userHandle, String newPicture) {
        String message;
        Table table = dynamoDB.getTable(TableName);
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();

        byte[] decodedPicture = Base64.getDecoder().decode(newPicture);
        InputStream inputStream = new ByteArrayInputStream(decodedPicture);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(decodedPicture.length);

        String url = null;
        try {
            s3.putObject(new PutObjectRequest(Bucket, userHandle, inputStream, metadata));
            url = s3.getUrl(Bucket, userHandle).toExternalForm();
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        }

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("userHandle", userHandle)
                                                            .withUpdateExpression("set profilePic=:p")
                                                            .withValueMap(new ValueMap().withString(":p", url))
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

    private String hash(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }
}
