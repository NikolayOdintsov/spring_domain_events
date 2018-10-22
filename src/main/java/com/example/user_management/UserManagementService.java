package com.example.user_management;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * It is not really a Domain service just because the app logic is pretty simple.
 */
@Service
@Slf4j
public class UserManagementService {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Injecting by constructor to make code testable with mocks
     *
     * @param userRepository
     * @param eventPublisher
     */
    public UserManagementService(UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }


    /**
     * Domain service operation.
     */
    @Transactional
    public void handleUpdateUserEvent(User user) {
        final Long userID = user.getId();

        log.info("Searching user by id: {} in storage.", userID);
        userRepository.findById(userID)
                .ifPresent(entity -> {

                    log.info("Domain event 'update' called for userId: {}", userID);
                    user.update();

                    log.info("Persisted changes into DB for userId: {}", userID);
                    userRepository.save(user);

                    /**
                     * This is a 2nd time event invocation
                     */
                    log.info("Published 'UserUpdateEvent' event  for userId: {}", userID);
                    eventPublisher.publishEvent(new UserUpdateEvent(entity.getId()));
                });
    }
}
