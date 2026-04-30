package com.example.OrganizationService.controller;

import com.example.OrganizationService.models.Organization;
import com.example.OrganizationService.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class EventController {

    @Autowired
    private EventService eventService;

    // GET /organization/event/user/{userId}
    @GetMapping("/event/user/{userId}")
    public ResponseEntity<?> getEventsByUser(@PathVariable int userId, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(401).body("Not logged in");
        return ResponseEntity.ok(eventService.getByUserAndOrganization(userId, org.getId()));
    }

    // GET /organization/event/dog/{dogId}
    @GetMapping("/event/dog/{dogId}")
    public ResponseEntity<?> getEventsByDog(@PathVariable int dogId, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(401).body("Not logged in");
        return ResponseEntity.ok(eventService.getByDogAndOrganization(dogId, org.getId()));
    }

    // GET /organization/event/location/{locationId}
    @GetMapping("/event/location/{locationId}")
    public ResponseEntity<?> getEventsByLocation(@PathVariable int locationId, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(401).body("Not logged in");
        return ResponseEntity.ok(eventService.getByLocation(locationId, org.getId()));
    }
}
