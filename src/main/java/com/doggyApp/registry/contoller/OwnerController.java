package com.doggyApp.registry.contoller;

import com.doggyApp.registry.models.Organization;
import com.doggyApp.registry.models.User;
import com.doggyApp.registry.service.OwnerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}