/*
 * Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.example.clutter.sdk;

import com.example.clutter.sdk.model.Empty;
import com.example.clutter.sdk.model.FollowerList;
import com.example.clutter.sdk.model.FollowingList;
import com.example.clutter.sdk.model.Message;
import com.example.clutter.sdk.model.StatusList;
import com.example.clutter.sdk.model.User;


@com.amazonaws.mobileconnectors.apigateway.annotation.Service(endpoint = "https://kogspst9ee.execute-api.us-west-2.amazonaws.com/Dev")
public interface CluttterClient {


    /**
     * A generic invoker to invoke any API Gateway endpoint.
     * @param request
     * @return ApiResponse
     */
    com.amazonaws.mobileconnectors.apigateway.ApiResponse execute(com.amazonaws.mobileconnectors.apigateway.ApiRequest request);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user", method = "OPTIONS")
    Empty userOptions();
    
    /**
     * 
     * 
     * @return StatusList
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/feed", method = "GET")
    StatusList userFeedGet();
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/feed", method = "OPTIONS")
    Empty userFeedOptions();
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/profilepic", method = "POST")
    Empty userProfilepicPost();
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/profilepic", method = "OPTIONS")
    Empty userProfilepicOptions();
    
    /**
     * 
     * 
     * @return Message
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/sendstatus", method = "POST")
    Message userSendstatusPost();
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/sendstatus", method = "OPTIONS")
    Empty userSendstatusOptions();
    
    /**
     * 
     * 
     * @param userhandle 
     * @return User
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}", method = "GET")
    User userUserhandleGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}", method = "OPTIONS")
    Empty userUserhandleOptions();
    
    /**
     * 
     * 
     * @param userhandle 
     * @param secondaryUser 
     * @return Message
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/follow/{secondaryUser}", method = "POST")
    Message userUserhandleFollowSecondaryUserPost(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle,
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "secondaryUser", location = "path")
                    String secondaryUser);
    
    /**
     * 
     * 
     * @param userhandle 
     * @param secondaryUser 
     * @return Message
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/follow/{secondaryUser}", method = "DELETE")
    Message userUserhandleFollowSecondaryUserDelete(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle,
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "secondaryUser", location = "path")
                    String secondaryUser);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/follow/{secondaryUser}", method = "OPTIONS")
    Empty userUserhandleFollowSecondaryUserOptions();
    
    /**
     * 
     * 
     * @param userhandle 
     * @return FollowerList
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/followers", method = "GET")
    FollowerList userUserhandleFollowersGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/followers", method = "OPTIONS")
    Empty userUserhandleFollowersOptions();
    
    /**
     * 
     * 
     * @param userhandle 
     * @return FollowingList
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/following", method = "GET")
    FollowingList userUserhandleFollowingGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/following", method = "OPTIONS")
    Empty userUserhandleFollowingOptions();
    
    /**
     * 
     * 
     * @param userhandle 
     * @return StatusList
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/story", method = "GET")
    StatusList userUserhandleStoryGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/story", method = "OPTIONS")
    Empty userUserhandleStoryOptions();
    
    /**
     * 
     * 
     * @param hashtag 
     * @return StatusList
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/{hashtag}", method = "GET")
    StatusList hashtagGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "hashtag", location = "path")
                    String hashtag);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/{hashtag}", method = "OPTIONS")
    Empty hashtagOptions();
    
}

