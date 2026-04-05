package com.doggyApp.registry.contoller;

import com.doggyApp.registry.models.Organization;
import com.doggyApp.registry.models.User;
import com.doggyApp.registry.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
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

    // PUT /user/password
    // User session only — changes the logged-in user's password.
    // Body: { "oldPassword": "...", "newPassword": "..." }
    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("User login required");
        }
        try {
            userService.changePassword(user.getId(), body.get("oldPassword"), body.get("newPassword"));
            return ResponseEntity.ok("Password updated");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // DELETE /user/{id}
    // Organization session only — deletes a user and cascades to all events they created.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) {
            return ResponseEntity.status(403).body("Organization login required");
        }
        try {
            userService.delete(id, org.getId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // GET /user/all
    // Organization session only — returns all users belonging to the logged-in org.
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) {
            return ResponseEntity.status(403).body("Organization login required");
        }
        return ResponseEntity.ok(userService.getByOrganization(org.getId()));
    }

    // POST /user/add
    // Organization session only — adds a user belonging to the logged-in org.
    // Body: { "firstName": "...", "lastName": "...", "email": "...", "password": "..." }
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) {
            return ResponseEntity.status(401).body("Organization login required");
        }
        try {
            return ResponseEntity.status(201).body(userService.create(user, org.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}