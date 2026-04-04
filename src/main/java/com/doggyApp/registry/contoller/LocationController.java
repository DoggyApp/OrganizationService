package com.doggyApp.registry.contoller;

import com.doggyApp.registry.models.Organization;
import com.doggyApp.registry.service.LocationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // GET /location/all
    // Organization session only — returns all locations belonging to the logged-in org.
    @GetMapping("/all")
    public ResponseEntity<?> getAllLocations(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(403).body("Organization login required");
        return ResponseEntity.ok(locationService.getByOrganization(org.getId()));
    }

    // POST /location/add
    // Organization session only — adds a new location to the logged-in org.
    // Body: { "name": "..." }
    @PostMapping("/add")
    public ResponseEntity<?> addLocation(@RequestBody String name, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(403).body("Organization login required");
        return ResponseEntity.status(201).body(locationService.add(name, org.getId()));
    }

    // DELETE /location/{id}
    // Organization session only — deletes a location that belongs to the logged-in org.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable int id, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(403).body("Organization login required");
        try {
            locationService.delete(id, org.getId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}