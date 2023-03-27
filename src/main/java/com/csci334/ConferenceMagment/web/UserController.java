package com.csci334.ConferenceMagment.web;

import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value= "{role}")
    public ResponseEntity<?> addUser(@RequestBody User user,@PathVariable String role){
       User newuser =  userService.addUser(user,role);
        return ResponseEntity.ok(newuser);
    }




}
