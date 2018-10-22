package com.example.user_management;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static com.example.user_management.User.Status.CREATED;
import static com.example.user_management.User.Status.UPDATED;

/**
 * Domain aggregate
 */
@Entity
@Data
@ToString(exclude = "domainEvents")
@Slf4j
public class User extends AbstractAggregateRoot<User> {

    enum Status {
        CREATED,
        UPDATED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Status status;

    public User(String name) {
        this.name = name;
        this.status = CREATED;
    }

    /**
     * Domain operation 'update a user'
     *
     * @return User entity
     */
    public User update() {
        this.status = UPDATED;

        /**
         * This is a 1st time event invocation
         */
        registerEvent(new UserUpdateEvent(id));
        return this;
    }
}
