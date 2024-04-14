package com.awbd.bookshop.repositories;

import com.awbd.bookshop.dtos.UserDetails;
import com.awbd.bookshop.dtos.UserResponse;
import com.awbd.bookshop.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.assertFalse;
@DataJpaTest
@ActiveProfiles("h2")
@Slf4j
public class UserRepositoryH2Test {

    @Autowired
    UserRepository userRepository;

    @Test
    void getUser() {
        Optional<UserResponse> user = userRepository.getUser("admin@yahoo.com", "password");
        assertFalse(user.isEmpty());
        assertNotNull(user.get());

        log.info("getUser - admin ...");
        log.info(user.get().getEmail());
        log.info(user.get().getAuthority());
        log.info(String.valueOf(user.get().getId()));
    }

    @Test
    void getUserByEmail() {
        Optional<User> user = userRepository.getUserByEmail("admin@yahoo.com");
        assertTrue(user.isPresent());
        assertNotNull(user.get());

        log.info("getUserByEmail - admin@yahoo.com ...");
        log.info(user.get().getEmail());
        log.info(user.get().getPassword());
        log.info(user.get().getAuthority().getAuthority());
    }

    @Test
    void getUserByEmail_notFound() {
        String nonExistentUserEmail = "non-existent-user@email.com";

        Optional<User> user = userRepository.getUserByEmail(nonExistentUserEmail);
        assertTrue(user.isEmpty());
        log.info("getUserByEmail_notFound - non-existent-user@email.com ...");
        log.info("User is empty: ");
        log.info(String.valueOf(user.isEmpty()));

    }

    @Test
    void getUsers() {
        List<UserDetails> users = userRepository.getUsers();
        assertNotNull(users);

        log.info("getUsers ...");
        users.forEach(user -> log.info(user.getLastName()));
    }

    @Test
    void findByUserName() {
        int userId = userRepository.findByUserName("user");

        log.info("findByUserName - user ...");
        log.info("UserId is not empty: ");
        log.info(String.valueOf(userId));
    }
}