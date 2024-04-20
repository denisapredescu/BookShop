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
        if (userRepository.count() == 0) {
            Authority authority_admin = new Authority("ROLE_ADMIN");
            authorityRepository.save(authority_admin);

            Authority authority_user = new Authority("ROLE_USER");
            authorityRepository.save(authority_user);

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setFirstName("admin_firstname");
            admin.setLastName("admin_lastname");
            admin.setAuthority(authority_admin);
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setEmail("user@gmail.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setFirstName("user_firstname");
            user.setLastName("user_lastname");
            user.setAuthority(authority_user);
            userRepository.save(user);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}

