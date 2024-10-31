package com.example.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void changePassword(String userName,String newPassword){
        InMemoryUserDetailsManager userDetailsManager = (InMemoryUserDetailsManager) userDetailsService;

        UserDetails userDetails = userDetailsManager.loadUserByUsername(userName);

        UserDetails updateUser = User.builder()
                .username(userDetails.getUsername())
                .password(passwordEncoder.encode(newPassword))
                .roles(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new))
                .build();
        userDetailsManager.updateUser(updateUser);
    }

    public void createUser(String username,String password,String... roles){
        InMemoryUserDetailsManager userDetailsManager = (InMemoryUserDetailsManager) userDetailsService;
        UserDetails newUser = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .build();

        userDetailsManager.createUser(newUser);
    }

    public void resetPassword(String username,String defaultPassword){
        changePassword(username,defaultPassword);
    }
}
