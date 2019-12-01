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

package com.example.clutter.sdk.model;

import com.example.clutter.sdk.model.GetFollowersFollowersItem;
import java.util.*;

public class GetFollowers {
    @com.google.gson.annotations.SerializedName("followers")
    private List<GetFollowersFollowersItem> followers = null;
    /**
     * Error message
     */
    @com.google.gson.annotations.SerializedName("message")
    private String message = null;
    /**
     * userhandle of the last follower in the list
     */
    @com.google.gson.annotations.SerializedName("lastKey")
    private String lastKey = null;

    /**
     * Gets followers
     *
     * @return followers
     **/
    public List<GetFollowersFollowersItem> getFollowers() {
        return followers;
    }

    /**
     * Sets the value of followers.
     *
     * @param followers the new value
     */
    public void setFollowers(List<GetFollowersFollowersItem> followers) {
        this.followers = followers;
    }

    /**
     * Error message
     *
     * @return message
     **/
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of message.
     *
     * @param message the new value
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * userhandle of the last follower in the list
     *
     * @return lastKey
     **/
    public String getLastKey() {
        return lastKey;
    }

    /**
     * Sets the value of lastKey.
     *
     * @param lastKey the new value
     */
    public void setLastKey(String lastKey) {
        this.lastKey = lastKey;
    }

}
