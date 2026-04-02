package com.doggyApp.registry.contoller;

import com.doggyApp.registry.models.Organization;
import com.doggyApp.registry.models.User;
import com.doggyApp.registry.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class EventController {

    @Autowired
    private EventService eventService;

    // GET /event/all
    // Organization session → returns every event in their org.
    // User session        → returns only that user's events.
    @GetMapping("/all")
    public ResponseEntity<?> getAllEvents(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org != null) {
            return ResponseEntity.ok(eventService.getByOrganization(org.getId()));
        }

        User user = (User) session.getAttribute("user");
        if (user != null) {
            return ResponseEntity.ok(eventService.getByUser(user.getId()));
        }

        return ResponseEntity.status(401).body("Not logged in");
    }
}