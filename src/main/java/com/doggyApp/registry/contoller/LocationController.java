package com.doggyApp.registry.contoller;

import com.doggyApp.registry.models.Organization;
import com.doggyApp.registry.service.LocationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/location")
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
    // Body: { "name": "...", "address": "..." }  (address is optional)
    @PostMapping("/add")
    public ResponseEntity<?> addLocation(@RequestBody Map<String, String> body, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(403).body("Organization login required");
        String name = body.get("name");
        if (name == null || name.isBlank()) return ResponseEntity.status(400).body("name is required");
        String address = body.get("address");
        if (address == null || address.isBlank()) return ResponseEntity.status(400).body("address is required");
        return ResponseEntity.status(201).body(locationService.add(name, address, org.getId()));
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