package com.awbd.bookshop.repositories;

import com.awbd.bookshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
//    @Query("SELECT DISTINCT user FROM User user WHERE user.email = :email AND user.password = :password")
//    Optional<User> getUser(String email, String password);
//
//    @Query("SELECT DISTINCT user FROM User user WHERE user.email = :email")
//    Optional<User> getUserByEmail(String email);
//
//    @Query("SELECT NEW com.master.bookstore_management.dtos.UserDetails(user.id, user.firstName, user.lastName, user.birthday, user.email, user.role) FROM User user")
//    List<UserDetails> getUsers();
}
