package com.myfreeit.passwordgenerator;

/*
 * Copyright (c) 2024, Denis Odesskiy. All rights reserved.
 *
 * This software is the confidential and proprietary information of Denis Odesskiy
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Denis Odesskiy.
 */

/**
 * Enum representing the status of a message in the application.
 *
 * <p>Each status is associated with a specific type of message to be displayed
 * in the UI, which can help the user understand whether the result is an error,
 * a notification, or a success message.</p>
 *
 * <ul>
 *   <li>{@link #ERROR} - Used to indicate that an error occurred.</li>
 *   <li>{@link #NOTIFICATION} - Used to display general notifications or information to the user.</li>
 *   <li>{@link #SUCCESS} - Used to indicate that an operation was successfully completed.</li>
 * </ul>
 *
 * @author Denis Odesskiy
 * @since 2024
 */
public enum MessageStatus {

    /**
     * Represents an error message.
     * Typically, shown when something goes wrong in the application.
     */
    ERROR,

    /**
     * Represents a notification message.
     * Used for general information messages that are neither errors nor successes.
     */
    NOTIFICATION,

    /**
     * Represents a success message.
     * Indicates that an action or process was completed successfully.
     */
    SUCCESS
}