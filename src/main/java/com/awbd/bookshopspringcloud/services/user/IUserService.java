package com.awbd.bookshopspringcloud.services.user;

import com.awbd.bookshopspringcloud.dtos.UpdateUser;
import com.awbd.bookshopspringcloud.dtos.UserDetails;
import com.awbd.bookshopspringcloud.dtos.UserResponse;
import com.awbd.bookshopspringcloud.models.User;

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
