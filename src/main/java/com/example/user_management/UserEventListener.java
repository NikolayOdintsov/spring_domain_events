package com.example.user_management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * This is Observer for domain events.
 */
@Service
@Slf4j
public class UserEventListener {

    /**
     * So we can define at what phase to register domain event.
     *
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void processUserUpdatedEvent(UserUpdateEvent event) {
        final Long userId = event.getUserId();

        // do any further actions with updated user
        //...

        log.info("Domain event 'UserUpdateEvent': {} processed", event);
    }
}
