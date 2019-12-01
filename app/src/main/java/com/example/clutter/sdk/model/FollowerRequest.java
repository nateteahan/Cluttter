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


public class FollowerRequest {
    /**
     * userhandle of the followee
     */
    @com.google.gson.annotations.SerializedName("userhandle")
    private String userhandle = null;
    /**
     * userhandle of the last follower in the list
     */
    @com.google.gson.annotations.SerializedName("lastFollower")
    private String lastFollower = null;

    /**
     * userhandle of the followee
     *
     * @return userhandle
     **/
    public String getUserhandle() {
        return userhandle;
    }

    /**
     * Sets the value of userhandle.
     *
     * @param userhandle the new value
     */
    public void setUserhandle(String userhandle) {
        this.userhandle = userhandle;
    }

    /**
     * userhandle of the last follower in the list
     *
     * @return lastFollower
     **/
    public String getLastFollower() {
        return lastFollower;
    }

    /**
     * Sets the value of lastFollower.
     *
     * @param lastFollower the new value
     */
    public void setLastFollower(String lastFollower) {
        this.lastFollower = lastFollower;
    }

}
