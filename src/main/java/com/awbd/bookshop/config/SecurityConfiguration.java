package com.awbd.bookshop.config;


import com.awbd.bookshop.services.security.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
    private final DataSource dataSource;
    //nouuuuuuuuu
    private final JpaUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(DataSource dataSource, JpaUserDetailsService userDetailsService) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;///////////nouu
    }
    //varianta mea
//    @Bean
//    public UserDetailsManager users(DataSource dataSource) {
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.setUsersByUsernameQuery("SELECT username,password,enabled from users where username = ?");
//        users.setAuthoritiesByUsernameQuery("SELECT u.username, a.authority FROM users u JOIN authorities a ON u.authority_id = a.id WHERE u.username = ?");
//        return users;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .authorizeRequests(requests -> requests//.requestMatchers("/admin").hasRole("ADMIN")

                       // .requestMatchers("/user").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/","/author","/book","/category","/login").permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                .requestMatchers("/author/update/**","/author/add","/author/delete/**").hasRole("ADMIN")
                                .requestMatchers("/book/update/**","/book/add","/book/addAuthorToBook/**",
                                        "/book/addCategBook/**","/book/delete/**").hasRole("ADMIN")
                                .requestMatchers("category/add","category/update/**","category/delete/**").hasRole("ADMIN")
                      //  .anyRequest().authenticated()//nou
                )
                .userDetailsService(userDetailsService)
//                .httpBasic(Customizer.withDefaults())
                .headers((headers) -> headers.disable())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                                .loginProcessingUrl("/perform_login")
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"));
        return http.build();
    }
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return NoOpPasswordEncoder.getInstance();//parola se salveaza fara encoder
//       // return new BCryptPasswordEncoder();
//    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        //return NoOpPasswordEncoder.getInstance();//parola se salveaza fara encoder
        return new BCryptPasswordEncoder();
    }

}