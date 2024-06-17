package com.awbd.bookshopspringcloud.services.user;

import com.awbd.bookshopspringcloud.dtos.UpdateUser;
import com.awbd.bookshopspringcloud.dtos.UserDetails;
import com.awbd.bookshopspringcloud.dtos.UserResponse;
import com.awbd.bookshopspringcloud.exceptions.exceptions.EmailAlreadyUsedException;
import com.awbd.bookshopspringcloud.exceptions.exceptions.NoFoundElementException;
import com.awbd.bookshopspringcloud.models.User;
import com.awbd.bookshopspringcloud.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User create(User newUser) {
        User user = userRepository.getUserByEmail(newUser.getEmail()).orElse(null);

        if (user == null)
            return userRepository.save(newUser);

        throw new EmailAlreadyUsedException("The email is already used by other user");
    }

    @Transactional
    @Override
    public User update(Integer id, UpdateUser updateUser) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NoFoundElementException("User not found"));

        user.setUsername(updateUser.getUsername());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());

        return userRepository.save(user);
    }

    @Override
    public UserResponse login(String email, String password) {
        return userRepository.getUser(email, password).orElseThrow(
                () -> new NoFoundElementException("User with this email and password not found"));
    }

    @Transactional
    @Override
    public List<UserDetails> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public User delete(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NoFoundElementException("User not found"));

        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public User getUser(int userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NoFoundElementException("User not found")
        );
    }

    @Override
    public int getId(String username){
        return userRepository.findByUserName(username);
    }

    @Override
    public Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if(authentication.getName()!="anonymousUser")
                return getId(authentication.getName());
            else
                return 0;
        }
        return 0;
    }
}
