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

import java.util.*;
import com.example.clutter.sdk.model.NewStatusListStatusesItem;

public class NewStatusList {
    @com.google.gson.annotations.SerializedName("statuses")
    private List<NewStatusListStatusesItem> statuses = null;
    /**
     * Error or success message
     */
    @com.google.gson.annotations.SerializedName("message")
    private String message = null;
    /**
     * Time attachment of the last evaluated status
     */
    @com.google.gson.annotations.SerializedName("lastKey")
    private String lastKey = null;

    /**
     * Gets statuses
     *
     * @return statuses
     **/
    public List<NewStatusListStatusesItem> getStatuses() {
        return statuses;
    }

    /**
     * Sets the value of statuses.
     *
     * @param statuses the new value
     */
    public void setStatuses(List<NewStatusListStatusesItem> statuses) {
        this.statuses = statuses;
    }

    /**
     * Error or success message
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
     * Time attachment of the last evaluated status
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
