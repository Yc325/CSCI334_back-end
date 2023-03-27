package com.csci334.ConferenceMagment.service;

import com.csci334.ConferenceMagment.domain.Authority;
import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.repository.AuthorityRepository;
import com.csci334.ConferenceMagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Calendar;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public User addUser(@RequestBody User user, String role) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        LocalDate date = LocalDate.now();

        //add User
        User newUser = new User();
        newUser.setAccountCreatedAt(date);
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);

        //add authority
        Authority authority = new Authority();
        authority.setUser(newUser);
        authority.setAuthority(role);


        authorityRepository.save(authority);
        return newUser;


    }

}
