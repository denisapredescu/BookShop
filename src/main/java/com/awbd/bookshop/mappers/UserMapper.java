package com.awbd.bookshop.mappers;

import com.awbd.bookshop.dtos.RequestUser;
import com.awbd.bookshop.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User requestUser(RequestUser user) {
        return new User(
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getFirstName(),
            user.getLastName(),
            true
        );
    }
}
