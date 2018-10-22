package com.example.user_management;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Aggregate repository
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
