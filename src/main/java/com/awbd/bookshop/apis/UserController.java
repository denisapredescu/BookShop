package com.awbd.bookshop.apis;


import com.awbd.bookshop.dtos.RequestUser;
import com.awbd.bookshop.dtos.UpdateUser;
import com.awbd.bookshop.dtos.UserDetails;
import com.awbd.bookshop.dtos.UserResponse;
import com.awbd.bookshop.mappers.UserMapper;
import com.awbd.bookshop.models.User;
import com.awbd.bookshop.services.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    final IUserService userService;
    final UserMapper mapper;

    public UserController(IUserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> create(
            @Valid @RequestBody RequestUser newUser){
        return ResponseEntity.ok(
                userService.create(mapper.requestUser(newUser))
        );
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<User> update(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateUser updateUser){
        return ResponseEntity.ok(userService.update(id, updateUser));
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<User> delete(
            @PathVariable Integer id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping("/login/{email}")
    public ResponseEntity<UserResponse> login(
            @PathVariable String email,
            @RequestHeader(name = "password") String password){
        return ResponseEntity.ok(userService.login(email, password));
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDetails>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }
}