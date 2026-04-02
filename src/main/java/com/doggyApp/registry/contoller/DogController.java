package com.doggyApp.registry.contoller;

import com.doggyApp.registry.models.Organization;
import com.doggyApp.registry.models.User;
import com.doggyApp.registry.service.DogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dog")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class DogController {

    @Autowired
    private DogService dogService;

    // Resolves the org ID from whichever session is active (org or user).
    private Integer resolveOrgId(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org != null) return org.getId();

        User user = (User) session.getAttribute("user");
        if (user != null) return user.getOrganizationId();

        return null;
    }

    // GET /dog/all
    // Returns all dogs in the logged-in org (accessible to both org and user sessions).
    @GetMapping("/all")
    public ResponseEntity<?> getAllDogs(HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        return ResponseEntity.ok(dogService.getByOrganization(orgId));
    }

    // GET /dog/{id}
    // Returns a single dog — only if it belongs to the logged-in org.
    @GetMapping("/{id}")
    public ResponseEntity<?> getDog(@PathVariable int id, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        try {
            return ResponseEntity.ok(dogService.getByIdAndOrganization(id, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Dog not found");
        }
    }
}