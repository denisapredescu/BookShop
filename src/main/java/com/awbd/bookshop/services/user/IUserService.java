package com.awbd.bookshop.services.user;

import com.awbd.bookshop.models.User;

import java.util.List;

public interface IUserService {
//    User create(User user);
//    User update(String token, Integer id, UpdateUser user);
//    UserResponse login(String email, String password);
//    List<UserDetails> getUsers(String token);
    void delete(String token, Integer id);
    User getUser(int userId);
}
