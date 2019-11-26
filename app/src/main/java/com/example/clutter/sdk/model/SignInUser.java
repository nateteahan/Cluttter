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


public class SignInUser {
    /**
     * The handle of the user to sign in
     */
    @com.google.gson.annotations.SerializedName("userhandle")
    private String userhandle = null;
    /**
     * Unsalted/hashed password of user
     */
    @com.google.gson.annotations.SerializedName("password")
    private String password = null;

    /**
     * The handle of the user to sign in
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
     * Unsalted/hashed password of user
     *
     * @return password
     **/
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of password.
     *
     * @param password the new value
     */
    public void setPassword(String password) {
        this.password = password;
    }

}