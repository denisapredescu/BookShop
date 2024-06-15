package com.awbd.bookshopspringcloud.repositories;

import com.awbd.bookshopspringcloud.dtos.UserDetails;
import com.awbd.bookshopspringcloud.dtos.UserResponse;
import com.awbd.bookshopspringcloud.models.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
class UserRepositoryTest {

    @Mock
    UserRepository userRepository;

    @Test
    void getUser() {
        // Create a user
        User user = new User("username", "user@gmail.com", "password", "firstname", "lastname", true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Mock
        when(userRepository.getUser(user.getEmail(), user.getPassword())).thenReturn(Optional.of(new UserResponse(user.getId(), user.getEmail(), user.getPassword())));

        // Call the getUser method
        Optional<UserResponse> actualUser = userRepository.getUser(user.getEmail(), user.getPassword());

        // Assertions
        assertTrue(actualUser.isPresent());
        assertNotNull(actualUser.get());
        assertEquals(user.getId(), actualUser.get().getId());
        assertEquals(user.getEmail(), actualUser.get().getEmail());
    }

    @Test
    void getUserByEmail() {
        // Create a user
        User user = new User("username", "user@gmail.com", "password", "firstname", "lastname", true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Mock
        when(userRepository.getUserByEmail("user@gmail.com")).thenReturn(Optional.of(user));

        // Call the getUserByEmail method
        Optional<User> actualUser = userRepository.getUserByEmail("user@gmail.com");

        // Assertions
        assertTrue(actualUser.isPresent());
        assertEquals(user, actualUser.get());
    }

    @Test
    void getUserByEmail_notFound() {
        // Mock
        when(userRepository.getUserByEmail(anyString())).thenReturn(Optional.empty());

        // Call the getUserByEmail method with a non-existent email
        Optional<User> actualUser = userRepository.getUserByEmail("nonexistent@gmail.com");

        // Assertion
        assertTrue(actualUser.isEmpty());
    }

    @Test
    void getUsers() {
        // Create a list of users
        List<UserDetails> userList = new ArrayList<>();

        // Mock
        when(userRepository.getUsers()).thenReturn(userList);

        // Call the getUsers method
        List<UserDetails> users = userRepository.getUsers();

        // Assertions
        assertNotNull(users);
        assertEquals(userList, users);
    }

    @Test
    void getUsers_notFound() {
        // Mock
        when(userRepository.getUsers()).thenReturn(new ArrayList<>());

        // Call the getUsers method
        List<UserDetails> users = userRepository.getUsers();

        // Assertion
        assertEquals(0, users.size());
    }

    @Test
    void findByUserName() {
        // Create a user
        User user = new User("username", "user@gmail.com", "password", "firstname", "lastname", true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Mock
        when(userRepository.findByUserName("username")).thenReturn(user.getId());

        // Call the findByUserName method
        int userId = userRepository.findByUserName("username");

        // Assertions
        assertEquals(user.getId(), userId);
    }
}
