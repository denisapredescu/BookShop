package com.awbd.bookshop.repositories;

import com.awbd.bookshop.dtos.UserDetails;
import com.awbd.bookshop.dtos.UserResponse;
import com.awbd.bookshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT DISTINCT user.id, user.email, user.authority.authority FROM User user WHERE user.email = :email AND user.password = :password")
    Optional<UserResponse> getUser(String email, String password);

    @Query("SELECT DISTINCT user FROM User user WHERE user.email = :email")
    Optional<User> getUserByEmail(String email);

    @Query("SELECT NEW com.awbd.bookshop.dtos.UserDetails(user.id, user.firstName, user.lastName, user.username, user.email) FROM User user")
    List<UserDetails> getUsers();
}
