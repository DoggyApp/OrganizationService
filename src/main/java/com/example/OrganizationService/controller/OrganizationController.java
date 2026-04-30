package com.example.OrganizationService.controller;

import com.example.OrganizationService.models.Organization;
import com.example.OrganizationService.models.User;
import com.example.OrganizationService.service.OrganizationService;
import com.example.OrganizationService.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    // POST /organization/register
    // Body: { "name": "...", "email": "...", "password": "..." }
    // Creates a new organization and stores it in session.
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Organization org, HttpSession session) {
        try {
            Organization created = organizationService.register(org);
            session.setAttribute("organization", created);
            return ResponseEntity.status(201).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // POST /organization/login
    // Body: { "email": "...", "password": "..." }
    // Returns the organization (password excluded) and stores it in session.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, HttpSession session) {
        try {
            Organization org = organizationService.login(
                    credentials.get("email"),
                    credentials.get("password")
            );
            session.setAttribute("organization", org);
            return ResponseEntity.ok(org);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // GET /organization/session
    // Returns the currently logged-in organization from session.
    @GetMapping("/session")
    public ResponseEntity<?> getSession(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        return ResponseEntity.ok(org);
    }

    // POST /organization/logout
    // Invalidates the session.
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }

    // PUT /organization/profile
    // Organization session only — updates name and email.
    // Body: { "name": "...", "email": "..." }
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> body, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        try {
            Organization updated = organizationService.updateProfile(org.getId(), body.get("name"), body.get("email"));
            session.setAttribute("organization", updated);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // PUT /organization/password
    // Organization session only — changes password.
    // Body: { "oldPassword": "...", "newPassword": "..." }
    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        try {
            organizationService.changePassword(org.getId(), body.get("oldPassword"), body.get("newPassword"));
            return ResponseEntity.ok("Password updated");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // PUT /organization/renew
    // Organization session only — resets subscription start to today, expiration to today + 1 year.
    @PutMapping("/renew")
    public ResponseEntity<?> renew(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        try {
            Organization updated = organizationService.renew(org.getId());
            session.setAttribute("organization", updated);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ── User management ───────────────────────────────────────────────────────

    // POST /organization/user/add
    // Organization session only — creates a user belonging to the logged-in org.
    // Body: { "firstName": "...", "lastName": "...", "email": "...", "password": "..." }
    @PostMapping("/user/add")
    public ResponseEntity<?> addUser(@RequestBody User user, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(401).body("Organization login required");
        try {
            return ResponseEntity.status(201).body(userService.create(user, org.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // DELETE /organization/user/{id}
    // Organization session only — deletes a user that belongs to the logged-in org.
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(403).body("Organization login required");
        try {
            userService.delete(id, org.getId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}