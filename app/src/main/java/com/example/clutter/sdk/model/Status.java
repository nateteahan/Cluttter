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


public class Status {
    /**
     * The name of the user who posted the status
     */
    @com.google.gson.annotations.SerializedName("firstName")
    private String firstName = null;
    /**
     * The handle of the user
     */
    @com.google.gson.annotations.SerializedName("userHandle")
    private String userHandle = null;
    /**
     * The time the status was posted
     */
    @com.google.gson.annotations.SerializedName("time")
    private String time = null;
    /**
     * The status to be displayed
     */
    @com.google.gson.annotations.SerializedName("status")
    private String status = null;
    /**
     * Optional photo to attach to status
     */
    @com.google.gson.annotations.SerializedName("imageAttachment")
    private String imageAttachment = null;
    /**
     * Optional video to attach to a status
     */
    @com.google.gson.annotations.SerializedName("videoAttachment")
    private String videoAttachment = null;

    /**
     * The name of the user who posted the status
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
     * The handle of the user
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
     * The time the status was posted
     *
     * @return time
     **/
    public String getTime() {
        return time;
    }

    /**
     * Sets the value of time.
     *
     * @param time the new value
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * The status to be displayed
     *
     * @return status
     **/
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of status.
     *
     * @param status the new value
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Optional photo to attach to status
     *
     * @return imageAttachment
     **/
    public String getImageAttachment() {
        return imageAttachment;
    }

    /**
     * Sets the value of imageAttachment.
     *
     * @param imageAttachment the new value
     */
    public void setImageAttachment(String imageAttachment) {
        this.imageAttachment = imageAttachment;
    }

    /**
     * Optional video to attach to a status
     *
     * @return videoAttachment
     **/
    public String getVideoAttachment() {
        return videoAttachment;
    }

    /**
     * Sets the value of videoAttachment.
     *
     * @param videoAttachment the new value
     */
    public void setVideoAttachment(String videoAttachment) {
        this.videoAttachment = videoAttachment;
    }

}
