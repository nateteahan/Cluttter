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

import com.example.clutter.sdk.model.Authorization;
import com.example.clutter.sdk.model.Empty;
import com.example.clutter.sdk.model.FollowerRequest;
import com.example.clutter.sdk.model.FollowingList;
import com.example.clutter.sdk.model.GetFollowers;
import com.example.clutter.sdk.model.GetFollowing;
import com.example.clutter.sdk.model.Hashtag;
import com.example.clutter.sdk.model.Message;
import com.example.clutter.sdk.model.NewStatusList;
import com.example.clutter.sdk.model.PostPicture;
import com.example.clutter.sdk.model.RegisterUser;
import com.example.clutter.sdk.model.SendStatusRequest;
import com.example.clutter.sdk.model.SignInUser;
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
     * @param userhandle 
     * @param body 
     * @return Message
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}", method = "POST")
    Message userUserhandlePost(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle,
            RegisterUser body);
    
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
     * @return StatusList
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/feed", method = "GET")
    StatusList userUserhandleFeedGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/feed", method = "OPTIONS")
    Empty userUserhandleFeedOptions();
    
    /**
     * 
     * 
     * @param userhandle 
     * @param lastKey 
     * @return NewStatusList
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/feed/{lastKey}", method = "GET")
    NewStatusList userUserhandleFeedLastKeyGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle,
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "lastKey", location = "path")
                    String lastKey);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/feed/{lastKey}", method = "OPTIONS")
    Empty userUserhandleFeedLastKeyOptions();
    
    /**
     * 
     * 
     * @param userhandle 
     * @param secondaryUser 
     * @return Message
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/follow/{secondaryUser}", method = "GET")
    Message userUserhandleFollowSecondaryUserGet(
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
     * @param body 
     * @return GetFollowers
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/followers", method = "GET")
    GetFollowers userUserhandleFollowersGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle,
            FollowerRequest body);
    
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
     * @param lastKey 
     * @return GetFollowers
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/followers/{lastKey}", method = "GET")
    GetFollowers userUserhandleFollowersLastKeyGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle,
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "lastKey", location = "path")
                    String lastKey);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/followers/{lastKey}", method = "OPTIONS")
    Empty userUserhandleFollowersLastKeyOptions();
    
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
     * @param lastkey 
     * @return GetFollowing
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/following/{lastkey}", method = "GET")
    GetFollowing userUserhandleFollowingLastkeyGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle,
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "lastkey", location = "path")
                    String lastkey);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/following/{lastkey}", method = "OPTIONS")
    Empty userUserhandleFollowingLastkeyOptions();
    
    /**
     * 
     * 
     * @param userhandle 
     * @param body 
     * @return Message
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/poststatus", method = "POST")
    Message userUserhandlePoststatusPost(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle,
            SendStatusRequest body);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/poststatus", method = "OPTIONS")
    Empty userUserhandlePoststatusOptions();
    
    /**
     * 
     * 
     * @param userhandle 
     * @param body 
     * @return Message
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/profilepic", method = "POST")
    Message userUserhandleProfilepicPost(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle,
            PostPicture body);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/profilepic", method = "OPTIONS")
    Empty userUserhandleProfilepicOptions();
    
    /**
     * 
     * 
     * @param userhandle 
     * @param body 
     * @return Authorization
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/signin", method = "POST")
    Authorization userUserhandleSigninPost(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle,
            SignInUser body);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/signin", method = "OPTIONS")
    Empty userUserhandleSigninOptions();
    
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
     * @param userhandle 
     * @param lastKey 
     * @return NewStatusList
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/story/{lastKey}", method = "GET")
    NewStatusList userUserhandleStoryLastKeyGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "userhandle", location = "path")
                    String userhandle,
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "lastKey", location = "path")
                    String lastKey);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/user/{userhandle}/story/{lastKey}", method = "OPTIONS")
    Empty userUserhandleStoryLastKeyOptions();
    
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
     * @param hashtag 
     * @param body 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/{hashtag}", method = "POST")
    Empty hashtagPost(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "hashtag", location = "path")
                    String hashtag,
            Hashtag body);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/{hashtag}", method = "OPTIONS")
    Empty hashtagOptions();
    
    /**
     * 
     * 
     * @param lastKey 
     * @param hashtag 
     * @return NewStatusList
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/{hashtag}/{lastKey}", method = "GET")
    NewStatusList hashtagLastKeyGet(
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "lastKey", location = "path")
                    String lastKey,
            @com.amazonaws.mobileconnectors.apigateway.annotation.Parameter(name = "hashtag", location = "path")
                    String hashtag);
    
    /**
     * 
     * 
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/{hashtag}/{lastKey}", method = "OPTIONS")
    Empty hashtagLastKeyOptions();
    
}

