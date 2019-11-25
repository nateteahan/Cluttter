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


public class Authorization {
    /**
     * Either a ssuccess message or an error message for the user to see
     */
    @com.google.gson.annotations.SerializedName("message")
    private String message = null;
    /**
     * The userhandle of the signed-in user
     */
    @com.google.gson.annotations.SerializedName("userhandle")
    private String userhandle = null;
    /**
     * Newly generated authorization token for the user
     */
    @com.google.gson.annotations.SerializedName("authToken")
    private String authToken = null;

    /**
     * Either a ssuccess message or an error message for the user to see
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
     * The userhandle of the signed-in user
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
     * Newly generated authorization token for the user
     *
     * @return authToken
     **/
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the value of authToken.
     *
     * @param authToken the new value
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
