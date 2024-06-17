package com.awbd.bookshopspringcloud.config;


import com.awbd.bookshopspringcloud.services.security.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    private final DataSource dataSource;

    private final JpaUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(DataSource dataSource, JpaUserDetailsService userDetailsService) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .authorizeRequests(requests -> requests

                        .requestMatchers("/","/book/**","/login/**").permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                              .requestMatchers("/book/update/**","/book/add","/book/addAuthorToBook/**",
                                   "/book/addCategBook/**","/book/delete/**").hasRole("ADMIN")
                )
                .userDetailsService(userDetailsService)
                .headers((headers) -> headers.disable())
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .csrf(csrf -> csrf.disable());

//                .formLogin(formLogin ->
//                        formLogin
//                                .loginPage("/login")
//                                .permitAll()
//                                .loginProcessingUrl("/perform_login")
//                )
                //.exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"));
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}