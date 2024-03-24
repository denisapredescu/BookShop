package com.awbd.bookshop.services.user;


import com.awbd.bookshop.models.User;
import com.awbd.bookshop.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService implements IUserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Transactional
//    @Override
//    public User create(User newUser) {
//        User user = userRepository.getUserByEmail(newUser.getEmail()).orElse(null);
//
//        if (user == null)
//            return userRepository.save(newUser);
//
//        throw new EmailAlreadyUsedException("The email is already used by other user");
//    }

//    @Transactional
//    @Override
//    public User update(String token, Integer id, UpdateUser updateUser) {
//        JwtUtil.verifyIsLoggedIn(token);
//
//        User user = userRepository.findById(id).orElseThrow(
//                () -> new NoSuchElementException("User not found"));
//
//        user.setBirthday(updateUser.getBirthday());
//        user.setFirstName(updateUser.getFirstName());
//        user.setLastName(updateUser.getLastName());
//
//        return userRepository.save(user);
//    }

//    @Override
//    public UserResponse login(String email, String password) {
//        User user = userRepository.getUser(email, password).orElseThrow(
//                () -> new NoSuchElementException("User with this email and password not found"));
//
//        return new UserResponse(
//                user.getId(),
//                user.getEmail(),
//                JwtUtil.generateToken(user.getFirstName() + user.getLastName(), user.getRole())
//        );
//    }

//    @Transactional
//    @Override
//    public List<UserDetails> getUsers(String token) {
//        JwtUtil.verifyAdmin(token);
//        return userRepository.getUsers();
//    }

    @Override
    public void delete(String token, Integer id) {
//        JwtUtil.verifyIsLoggedIn(token);

        User user = userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found"));

        userRepository.delete(user);
    }

    @Override
    public User getUser(int userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("User not found")
        );
    }
}
