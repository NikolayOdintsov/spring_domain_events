package com.example.user_management;

import lombok.Value;

/**
 * Domain event
 */

@Value
public class UserUpdateEvent {
    /**
     * Should be immutable as it is Value object
     */
    private final Long userId;
}
