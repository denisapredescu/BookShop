package com.awbd.bookshop.bootstrap;


import com.awbd.bookshop.models.*;
import com.awbd.bookshop.repositories.AuthorityRepository;
import com.awbd.bookshop.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("mysql")
public class DataLoader implements CommandLineRunner {

    private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DataLoader(AuthorityRepository authorityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void loadUserData() {
        if (userRepository.count() == 5){

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("adminul@yahoo.com");
            admin.setPassword(passwordEncoder.encode("pass"));
            admin.setFirstName("Sara");
            admin.setLastName("Pan");
            admin.setAuthority(new Authority(2,"ROLE_ADMIN"));


            User user = new User();
            user.setUsername("miruna");
            user.setEmail("miruna@yahoo.com");
            user.setPassword(passwordEncoder.encode("pass"));
            user.setFirstName("Miruna");
            user.setLastName("Pos");
            user.setAuthority(new Authority(1,"ROLE_USER"));


            userRepository.save(admin);
            userRepository.save(user);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}

