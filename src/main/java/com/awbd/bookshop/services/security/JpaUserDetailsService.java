package com.awbd.bookshop.services.security;

import com.awbd.bookshop.models.Authority;
import com.awbd.bookshop.models.User;
import com.awbd.bookshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
@RequiredArgsConstructor
@Slf4j
//@Profile("mysql")
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;

        Optional<User> userOpt= userRepository.findByUsername(username);
        if (userOpt.isPresent())
            user = userOpt.get();
        else
            throw new UsernameNotFoundException("Username: " + username);

        log.info(user.toString());
        Set<Authority> authorities = new HashSet<>();
        authorities.add(user.getAuthority());

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),user.isEnabled(),true,true,
                true, getAuthorities(authorities));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Authority> authorities) {
        if (authorities == null){
            return new HashSet<>();
        } else if (authorities.size()==0) {
            return new HashSet<>();
        } else{
            return authorities.stream()
                    .map(Authority::getAuthority)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
    }
}
