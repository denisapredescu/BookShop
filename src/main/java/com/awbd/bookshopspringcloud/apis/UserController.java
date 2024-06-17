package com.awbd.bookshopspringcloud.apis;


import com.awbd.bookshopspringcloud.dtos.RequestUser;
import com.awbd.bookshopspringcloud.dtos.UserDetails;
import com.awbd.bookshopspringcloud.dtos.UserResponse;
import com.awbd.bookshopspringcloud.mappers.UserMapper;
import com.awbd.bookshopspringcloud.models.Authority;
import com.awbd.bookshopspringcloud.models.User;
import com.awbd.bookshopspringcloud.services.authority.IAuthorityService;
import com.awbd.bookshopspringcloud.services.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "Endpoints for managing users")
public class UserController {
    final IUserService userService;
    final UserMapper mapper;
    private PasswordEncoder passwordEncoder;

    private IAuthorityService authorityService;

    public UserController(IUserService userService, UserMapper mapper, PasswordEncoder passwordEncoder,
                         IAuthorityService authorityService) {
        this.userService = userService;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
    }
    //    @PostMapping("/signUp")
    //    public ModelAndView add(
    //            @Valid @ModelAttribute RequestUser newUser
    //            ){
    //        User user = mapper.requestUser(newUser);
    //        user.setEnabled(true);
    //        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
    //        Authority authority = authorityService.getAuthorityByName("ROLE_USER");
    //        user.setAuthority(authority);
    //
    //        userService.create(user);
    //        return new ModelAndView("redirect:/");
    //    }
    @Operation(summary = "Create a new user", responses = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/signUp")
    public ResponseEntity<User> add(
           @Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User details", required = true) RequestUser newUser
            ){
        User user = mapper.requestUser(newUser);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Authority authority = authorityService.getAuthorityByName("ROLE_USER");
        user.setAuthority(authority);

        User theNewUser = userService.create(user);
        return ResponseEntity.ok(theNewUser);
    }

    @Operation(summary = "Login and retrieve user details (user ID, email)", responses = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/login/{email}")
    public ResponseEntity<UserResponse> login(
            @PathVariable @Parameter(description = "User email", required = true) String email,
            @RequestHeader(name = "password") @Parameter(description = "User password", required = true) String password){
        return ResponseEntity.ok(userService.login(email, password));
    }

    @Operation(summary = "Get a list of all users. Just an user with an admin role can access the users", responses = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserDetails>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

}