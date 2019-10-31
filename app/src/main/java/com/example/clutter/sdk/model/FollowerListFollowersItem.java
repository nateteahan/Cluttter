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


public class FollowerListFollowersItem {
    /**
     * The handle of the user who is following
     */
    @com.google.gson.annotations.SerializedName("userHandle")
    private String userHandle = null;

    /**
     * The handle of the user who is following
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

}
