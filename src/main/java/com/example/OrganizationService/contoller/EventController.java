package com.example.OrganizationService.contoller;

import com.example.OrganizationService.models.Organization;
import com.example.OrganizationService.models.User;
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

    private Integer resolveOrgId(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org != null) return org.getId();
        User user = (User) session.getAttribute("user");
        if (user != null) return user.getOrganizationId();
        return null;
    }

    // GET /organization/event/user/{userId}
    // Org session: can request events for any user in their org.
    // User session: can only request their own events.
    @GetMapping("/event/user/{userId}")
    public ResponseEntity<?> getEventsByUser(@PathVariable int userId, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org != null) {
            return ResponseEntity.ok(eventService.getByUserAndOrganization(userId, org.getId()));
        }
        User user = (User) session.getAttribute("user");
        if (user == null) return ResponseEntity.status(401).body("Not logged in");
        if (user.getId() != userId) return ResponseEntity.status(403).body("You can only view your own events");
        return ResponseEntity.ok(eventService.getByUserAndOrganization(userId, user.getOrganizationId()));
    }

    // GET /organization/event/dog/{dogId}
    @GetMapping("/event/dog/{dogId}")
    public ResponseEntity<?> getEventsByDog(@PathVariable int dogId, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        return ResponseEntity.ok(eventService.getByDogAndOrganization(dogId, orgId));
    }

    // GET /organization/event/location/{locationId}
    @GetMapping("/event/location/{locationId}")
    public ResponseEntity<?> getEventsByLocation(@PathVariable int locationId, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        return ResponseEntity.ok(eventService.getByLocation(locationId, orgId));
    }
}
