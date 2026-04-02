package com.doggyApp.registry.contoller;

import com.doggyApp.registry.models.Organization;
import com.doggyApp.registry.service.OrganizationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/organization")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

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
}