package com.doggyApp.registry.contoller;

import com.doggyApp.registry.models.Event;
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

    private Integer resolveOrgId(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org != null) return org.getId();
        User user = (User) session.getAttribute("user");
        if (user != null) return user.getOrganizationId();
        return null;
    }

    // GET /event/user/{userId}
    // Org session: can request events for any user in their org.
    // User session: can only request their own events.
    @GetMapping("/user/{userId}")
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

    // GET /event/dog/{dogId}
    @GetMapping("/dog/{dogId}")
    public ResponseEntity<?> getEventsByDog(@PathVariable int dogId, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        return ResponseEntity.ok(eventService.getByDogAndOrganization(dogId, orgId));
    }

    // GET /event/location/{locationId}
    // Returns all events at the given location, scoped to the logged-in org.
    // Accessible to both org and user sessions.
    @GetMapping("/location/{locationId}")
    public ResponseEntity<?> getEventsByLocation(@PathVariable int locationId, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org != null) {
            return ResponseEntity.ok(eventService.getByLocation(locationId, org.getId()));
        }
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return ResponseEntity.ok(eventService.getByLocation(locationId, user.getOrganizationId()));
        }
        return ResponseEntity.status(401).body("Not logged in");
    }

    // PUT /event/{id}/join
    // User session only — assigns the logged-in user to an existing event.
    @PutMapping("/{id}/join")
    public ResponseEntity<?> joinEvent(@PathVariable int id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("User login required");
        }
        try {
            return ResponseEntity.ok(eventService.assignUser(id, user.getId(), user.getOrganizationId()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // PUT /event/{id}/dog?dogId=1
    // User session only — assigns a dog to an existing event.
    // The dog must belong to the same org as the user.
    @PutMapping("/{id}/dog")
    public ResponseEntity<?> addDogToEvent(@PathVariable int id,
                                           @RequestParam int dogId,
                                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("User login required");
        }
        try {
            return ResponseEntity.ok(eventService.assignDog(id, dogId, user.getOrganizationId()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // POST /event/add
    // User session only — creates an event and stamps the logged-in user as the creator.
    // Organizations are not permitted to create events.
    // Dogs can be assigned afterwards via PUT /event/{id}/dog
    // Body: { "event": "...", "description": "...", "startTime": "...", "endTime": "..." }
    // startTime / endTime format: "2026-04-06T09:00:00"
    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@RequestBody Event event, HttpSession session) {
        if (session.getAttribute("organization") != null) {
            return ResponseEntity.status(403).body("Organizations cannot create events");
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("User login required");
        }
        try {
            event.setCreator(user);
            return ResponseEntity.status(201).body(eventService.create(event, user.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // DELETE /event/{id}
    // User session only — only the user who originally created the event may delete it.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("organization") != null) {
            return ResponseEntity.status(403).body("Organizations cannot delete events");
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("User login required");
        }
        try {
            eventService.delete(id, user.getId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // PUT /event/{id}/edit
    // User session only — only the user who originally created the event may edit it.
    // Organizations cannot edit events.
    // Body: { "event": "...", "description": "...", "place": "...", "startTime": "...", "endTime": "..." }
    @PutMapping("/{id}/edit")
    public ResponseEntity<?> editEvent(@PathVariable int id,
                                       @RequestBody Event updates,
                                       HttpSession session) {
        if (session.getAttribute("organization") != null) {
            return ResponseEntity.status(403).body("Organizations cannot edit events");
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("User login required");
        }
        try {
            Event event = eventService.getById(id);
            if (user.getId() != event.getCreatorId()) {
                return ResponseEntity.status(403).body("Only the creator of this event can edit it");
            }
            return ResponseEntity.ok(eventService.update(id, updates, user.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // GET /event/all
//    // Organization session → returns every event in their org.
//    // User session        → returns only that user's events.
//    @GetMapping("/all")
//    public ResponseEntity<?> getAllEvents(HttpSession session) {
//        Organization org = (Organization) session.getAttribute("organization");
//        if (org != null) {
//            return ResponseEntity.ok(eventService.getByOrganization(org.getId()));
//        }
//
//        User user = (User) session.getAttribute("user");
//        if (user != null) {
//            return ResponseEntity.ok(eventService.getByUser(user.getId()));
//        }
//
//        return ResponseEntity.status(401).body("Not logged in");
//    }

}