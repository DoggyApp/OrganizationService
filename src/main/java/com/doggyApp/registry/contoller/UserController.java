package com.doggyApp.registry.contoller;

import com.doggyApp.registry.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

//    @PostMapping("/users")
//    public User create(@Valid @RequestBody User user) {
//        return UserService.create(user);
//    }

}

