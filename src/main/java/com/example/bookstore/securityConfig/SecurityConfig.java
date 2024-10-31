package com.example.bookstore.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests(authorize ->authorize
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")
                .anyRequest().permitAll()
        )
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/admin/dashboard",true)
                ).logout(logout-> logout
                        .permitAll())
                .csrf(csrf-> csrf.disable());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails newAdmin = User.builder()
                .username("newadmin")
                .password(passwordEncoder().encode("newpassword"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin,newAdmin);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
