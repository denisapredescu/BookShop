package com.awbd.bookshopspringcloud.mappers;

import com.awbd.bookshopspringcloud.dtos.RequestUser;
import com.awbd.bookshopspringcloud.models.User;
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
