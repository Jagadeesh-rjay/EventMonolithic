package com.rjay.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.rjay.user.entity.EventManager;
import com.rjay.user.entity.User;
import com.rjay.user.service.EventManagerService;
import com.rjay.user.service.UserService;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private EventManagerService eventManagerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	User user = userService.getbyemail(email);
        if (user != null) {
            return new CustomUserDetails(user);
        }

        EventManager manager = eventManagerService.getbyemail(email);
        if (manager != null) {
            return new CustomEventMangerDetails(manager);
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}