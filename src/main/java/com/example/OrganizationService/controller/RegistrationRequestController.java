package com.example.OrganizationService.controller;

import com.example.OrganizationService.models.Organization;
import com.example.OrganizationService.service.RegistrationRequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class RegistrationRequestController {

    @Autowired
    private RegistrationRequestService registrationRequestService;

    // GET /organization/registration-requests/pending
    @GetMapping("/registration-requests/pending")
    public ResponseEntity<?> getPendingRequests(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(registrationRequestService.getPendingRequests(org.getId()));
    }

    // POST /organization/registration-request/{requestId}/accept
    @PostMapping("/registration-request/{requestId}/accept")
    public ResponseEntity<?> acceptRequest(@PathVariable int requestId, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(401).build();
        try {
            registrationRequestService.acceptRequest(requestId, org.getId());
            return ResponseEntity.ok().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST /organization/registration-request/{requestId}/reject
    @PostMapping("/registration-request/{requestId}/reject")
    public ResponseEntity<?> rejectRequest(@PathVariable int requestId, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(401).build();
        try {
            registrationRequestService.rejectRequest(requestId, org.getId());
            return ResponseEntity.ok().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
