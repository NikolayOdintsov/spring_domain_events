package com.example.user_management;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserManagementTest {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldUpdateUserDataAndGenerateDomainEvent() throws Exception {
        //given
        User user = new User("John");
        userRepository.save(user);


        //when
        user.setName("Alex");
        userManagementService.handleUpdateUserEvent(user);

        //then
        Optional<User> result = userRepository.findById(user.getId());

        assertTrue(result.isPresent());

        User updatedUser = result.get();
        assertNotNull(updatedUser);

        assertEquals(user.getName(), updatedUser.getName());
        assertEquals(User.Status.UPDATED, updatedUser.getStatus());
    }
}
