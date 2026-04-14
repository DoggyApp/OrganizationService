package com.example.OrganizationService.contoller;

import com.example.OrganizationService.models.Organization;
import com.example.OrganizationService.models.User;
import com.example.OrganizationService.service.OwnerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    private Integer resolveOrgId(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org != null) return org.getId();

        User user = (User) session.getAttribute("user");
        if (user != null) return user.getOrganizationId();

        return null;
    }

    // GET /owner/all
    @GetMapping("/all")
    public ResponseEntity<?> getAllOwners(HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        return ResponseEntity.ok(ownerService.getByOrganization(orgId));
    }

    // POST /owner/add
    // Body: { "name": "...", "email": "...", "phoneNumber": "..." }
    @PostMapping("/add")
    public ResponseEntity<?> addOwner(@RequestBody Map<String, String> body, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        String name = body.get("name");
        if (name == null || name.isBlank()) return ResponseEntity.status(400).body("name is required");
        try {
            return ResponseEntity.status(201).body(ownerService.addOwner(name, body.get("email"), body.get("phoneNumber")));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // POST /owner/{id}/dog
    // Body: { "name": "...", "breed": "...", "age": 3, "weight": 50 }
    @PostMapping("/{id}/dog")
    public ResponseEntity<?> addDogToOwner(@PathVariable int id,
                                           @RequestBody Map<String, Object> body,
                                           HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            String name   = (String) body.get("name");
            String breed  = (String) body.get("breed");
            int age       = (Integer) body.get("age");
            int weight    = (Integer) body.get("weight");
            return ResponseEntity.status(201).body(ownerService.addDogToOwner(id, name, breed, age, weight, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // DELETE /owner/{id}/dog/{dogId}
    // Returns updated Owner if dogs remain, or 204 if owner was also deleted.
    @DeleteMapping("/{id}/dog/{dogId}")
    public ResponseEntity<?> removeDogFromOwner(@PathVariable int id,
                                                @PathVariable int dogId,
                                                HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            var updated = ownerService.removeDogFromOwner(id, dogId, orgId);
            return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}