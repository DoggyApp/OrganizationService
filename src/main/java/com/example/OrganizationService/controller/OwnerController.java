package com.example.OrganizationService.controller;

import com.example.OrganizationService.models.Organization;
import com.example.OrganizationService.models.User;
import com.example.OrganizationService.service.OwnerService;
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