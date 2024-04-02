package com.awbd.bookshop.repositories;

import com.awbd.bookshop.dtos.UserDetails;
import com.awbd.bookshop.dtos.UserResponse;
import com.awbd.bookshop.models.Category;
import com.awbd.bookshop.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

//    @Test
//    void getUser() {
//        User user = new User("username", "user@gmail.com", "password", "firstname", "lastname", true);
//        User savedUser = userRepository.save(user);
//
//        Optional<UserResponse> actualUser = userRepository.getUser(savedUser.getEmail(), savedUser.getPassword());
//        assertTrue(actualUser.isPresent());
//        assertNotNull(actualUser.get());
//        assertEquals(savedUser.getId(), actualUser.get().getId());
//        assertEquals(savedUser.getEmail(), actualUser.get().getEmail());
//    }

    @Test
    void getUserByEmail() {
        User user = new User("username", "user@gmail.com", "password", "firstname", "lastname", true);
        User savedUser = userRepository.save(user);

        Optional<User> actualUser = userRepository.getUserByEmail(savedUser.getEmail());
        assertTrue(actualUser.isPresent());
        assertNotNull(actualUser.get());
        assertEquals(savedUser, actualUser.get());
    }

    @Test
    void getUserByEmail_notFound() {
        String nonExistentUserEmail = "user@email.com";

        Optional<User> actualUser = userRepository.getUserByEmail(nonExistentUserEmail);
        assertTrue(actualUser.isEmpty());
    }

    @Test
    void getUsers() {
        User user = new User("username", "user@gmail.com", "password", "firstname", "lastname", true);
        User savedUser = userRepository.save(user);

        List<UserDetails> users = userRepository.getUsers();
        assertEquals(1, users.size());
        assertEquals(savedUser.getId(), users.get(0).getId());
        assertEquals(savedUser.getFirstName(), users.get(0).getFirstName());
        assertEquals(savedUser.getLastName(), users.get(0).getLastName());
        assertEquals(savedUser.getUsername(), users.get(0).getUsername());
        assertEquals(savedUser.getEmail(), users.get(0).getEmail());
    }

    @Test
    void getUsers_notFound() {
        List<UserDetails> users = userRepository.getUsers();
        assertEquals(0, users.size());
    }

    @Test
    void findByUserName() {
        User user = new User("username", "user@gmail.com", "password", "firstname", "lastname", true);
        User savedUser = userRepository.save(user);

        int userId = userRepository.findByUserName(savedUser.getUsername());
        assertEquals(savedUser.getId(), userId);
    }
}