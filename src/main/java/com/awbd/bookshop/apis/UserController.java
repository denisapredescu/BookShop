package com.awbd.bookshop.apis;


import com.awbd.bookshop.models.User;
import com.awbd.bookshop.services.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/signUp")
//    public ResponseEntity<User> create(
//            @Valid @RequestBody User newUser){
//        return ResponseEntity.ok(userService.create(newUser));
//    }
//
//    @PatchMapping("/update/{id}")
//    public ResponseEntity<User> update(
//            @RequestHeader(name = "userToken") String token,
//            @PathVariable Integer id,
//            @Valid @RequestBody UpdateUser updateUser){
//        return ResponseEntity.ok(userService.update(token, id, updateUser));
//    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader(name = "userToken") String token,
            @PathVariable Integer id){
        userService.delete(token, id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/login/{email}")
//    public ResponseEntity<UserResponse> login(
//            @PathVariable String email,
//            @RequestHeader(name = "password") String password){
//        return ResponseEntity.ok(userService.login(email, password));
//    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        return ResponseEntity.ok(userService.getUser(id));
    }

//    @GetMapping("/getUsers")
//    public ResponseEntity<List<UserDetails>> getUsers(
//            @RequestHeader(name = "userToken") String token){
//        return ResponseEntity.ok(userService.getUsers(token));
//    }
}