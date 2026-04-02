package com.doggyApp.registry.contoller;

import com.doggyApp.registry.models.User;
import com.doggyApp.registry.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /user/login
    // Body: { "email": "...", "password": "..." }
    // Returns the user (password excluded) and stores them in session.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        try {
            User user = userService.login(
                    credentials.get("email"),
                    credentials.get("password")
            );
            session.setAttribute("user", user);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // GET /user/session
    // Returns the currently logged-in user from session.
    @GetMapping("/session")
    public ResponseEntity<?> getSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        return ResponseEntity.ok(user);
    }

    // POST /user/logout
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }
}