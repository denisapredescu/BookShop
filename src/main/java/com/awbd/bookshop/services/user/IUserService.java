package com.awbd.bookshop.services.user;

import com.awbd.bookshop.dtos.UpdateUser;
import com.awbd.bookshop.dtos.UserDetails;
import com.awbd.bookshop.dtos.UserResponse;
import com.awbd.bookshop.models.User;

import java.util.List;

public interface IUserService {
    User create(User user);
    User update(Integer id, UpdateUser user);
    UserResponse login(String email, String password);
    List<UserDetails> getUsers();
    User delete(Integer id);
    User getUser(int userId);
    int getId(String username);
    Integer getCurrentUserId();
}
