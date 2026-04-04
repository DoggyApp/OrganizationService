package com.doggyApp.registry.contoller;

import com.doggyApp.registry.models.*;
import com.doggyApp.registry.service.DogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/dog")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class DogController {

    @Autowired
    private DogService dogService;

    // Resolves the org ID from whichever session is active (org or user).
    private Integer resolveOrgId(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org != null) return org.getId();

        User user = (User) session.getAttribute("user");
        if (user != null) return user.getOrganizationId();

        return null;
    }

    // GET /dog/all
    // Returns all dogs in the logged-in org (accessible to both org and user sessions).
    @GetMapping("/all")
    public ResponseEntity<?> getAllDogs(HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        return ResponseEntity.ok(dogService.getByOrganization(orgId));
    }

    // GET /dog/{id}
    // Returns a single dog — only if it belongs to the logged-in org.
    @GetMapping("/{id}")
    public ResponseEntity<?> getDog(@PathVariable int id, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        try {
            return ResponseEntity.ok(dogService.getByIdAndOrganization(id, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Dog not found");
        }
    }

    // POST /dog/{id}/alert
    // Body: { "alert": "..." }
    @PostMapping("/{id}/alert")
    public ResponseEntity<?> addAlert(@PathVariable int id,
                                      @RequestBody Alert alert,
                                      HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.status(201).body(dogService.addAlert(id, alert, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // POST /dog/{id}/note
    // Body: { "note": "..." }
    @PostMapping("/{id}/note")
    public ResponseEntity<?> addNote(@PathVariable int id,
                                     @RequestBody Note note,
                                     HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.status(201).body(dogService.addNote(id, note, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // POST /dog/{id}/like
    // Body: { "like": "..." }
    @PostMapping("/{id}/like")
    public ResponseEntity<?> addLike(@PathVariable int id,
                                     @RequestBody Like like,
                                     HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.status(201).body(dogService.addLike(id, like, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // PUT /dog/{id}/owner
    // Body: { "name": "...", "email": "...", "phoneNumber": "..." }
    @PutMapping("/{id}/owner")
    public ResponseEntity<?> setOwner(@PathVariable int id,
                                      @RequestBody Owner owner,
                                      HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.ok(dogService.setOwner(id, owner, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // PUT /dog/alert/{id}
    @PutMapping("/alert/{id}")
    public ResponseEntity<?> updateAlert(@PathVariable int id,
                                         @RequestBody Alert alert,
                                         HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.ok(dogService.updateAlert(id, alert, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // DELETE /dog/alert/{id}
    @DeleteMapping("/alert/{id}")
    public ResponseEntity<?> deleteAlert(@PathVariable int id, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            dogService.deleteAlert(id, orgId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // PUT /dog/note/{id}
    @PutMapping("/note/{id}")
    public ResponseEntity<?> updateNote(@PathVariable int id,
                                        @RequestBody Note note,
                                        HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.ok(dogService.updateNote(id, note, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // DELETE /dog/note/{id}
    @DeleteMapping("/note/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable int id, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            dogService.deleteNote(id, orgId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // PUT /dog/like/{id}
    @PutMapping("/like/{id}")
    public ResponseEntity<?> updateLike(@PathVariable int id,
                                        @RequestBody Like like,
                                        HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.ok(dogService.updateLike(id, like, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // DELETE /dog/like/{id}
    @DeleteMapping("/like/{id}")
    public ResponseEntity<?> deleteLike(@PathVariable int id, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            dogService.deleteLike(id, orgId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // GET /dog/{id}/vaccines
    @GetMapping("/{id}/vaccines")
    public ResponseEntity<?> getVaccines(@PathVariable int id, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.ok(dogService.getVaccinesByDog(id, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // PUT /dog/{dogId}/vaccine/{vaccineId}/renew
    // One-click renewal — sets vaccinated date to today and auto-calculates expiration.
    @PutMapping("/{dogId}/vaccine/{vaccineId}/renew")
    public ResponseEntity<?> renewVaccine(@PathVariable int dogId,
                                          @PathVariable int vaccineId,
                                          HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.ok(dogService.renewVaccine(vaccineId, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // POST /dog/{id}/vaccine
    // Adds a custom vaccine with a manually provided expiration date.
    // Body: { "name": "...", "vaccinatedDate": "2026-04-01", "expirationDate": "2027-04-01" }
    @PostMapping("/{id}/vaccine")
    public ResponseEntity<?> addCustomVaccine(@PathVariable int id,
                                              @RequestBody Vaccine vaccine,
                                              HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.status(201).body(dogService.addCustomVaccine(id, vaccine, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // DELETE /dog/vaccine/{id}
    // Custom vaccines only — standard vaccines (Bordetella, Rabies) cannot be deleted.
    @DeleteMapping("/vaccine/{id}")
    public ResponseEntity<?> deleteVaccine(@PathVariable int id, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            dogService.deleteVaccine(id, orgId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // DELETE /dog/{id}
    // Organization session only — deletes a dog that belongs to the logged-in org.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDog(@PathVariable int id, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) {
            return ResponseEntity.status(403).body("Organization login required");
        }
        try {
            dogService.delete(id, org.getId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // POST /dog/add?bordetellaDate=2026-03-01&rabiesDate=2026-03-01
    // Org or user session — adds a dog to the logged-in org.
    // Both vaccination dates are required; expiration dates are calculated automatically.
    // Body: { "name": "...", "breed": "...", "age": ..., "weight": ..., "image": "..." }
    @PostMapping("/add")
    public ResponseEntity<?> addDog(@RequestBody Dog dog,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bordetellaDate,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rabiesDate,
                                    HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        try {
            return ResponseEntity.status(201).body(dogService.create(dog, orgId, bordetellaDate, rabiesDate));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}