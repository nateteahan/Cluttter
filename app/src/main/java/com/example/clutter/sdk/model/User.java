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


public class User {
    /**
     * The URL of the profile picture of the users
     */
    @com.google.gson.annotations.SerializedName("profilePic")
    private String profilePic = null;
    /**
     * The handle of the user who posted the status
     */
    @com.google.gson.annotations.SerializedName("userHandle")
    private String userHandle = null;
    /**
     * The first name of the user
     */
    @com.google.gson.annotations.SerializedName("firstName")
    private String firstName = null;
    /**
     * The last name of the user
     */
    @com.google.gson.annotations.SerializedName("lastName")
    private String lastName = null;
    /**
     * The email of the user
     */
    @com.google.gson.annotations.SerializedName("email")
    private String email = null;

    /**
     * The URL of the profile picture of the users
     *
     * @return profilePic
     **/
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Sets the value of profilePic.
     *
     * @param profilePic the new value
     */
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    /**
     * The handle of the user who posted the status
     *
     * @return userHandle
     **/
    public String getUserHandle() {
        return userHandle;
    }

    /**
     * Sets the value of userHandle.
     *
     * @param userHandle the new value
     */
    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    /**
     * The first name of the user
     *
     * @return firstName
     **/
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of firstName.
     *
     * @param firstName the new value
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * The last name of the user
     *
     * @return lastName
     **/
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of lastName.
     *
     * @param lastName the new value
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * The email of the user
     *
     * @return email
     **/
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of email.
     *
     * @param email the new value
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
