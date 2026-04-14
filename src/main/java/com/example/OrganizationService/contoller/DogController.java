package com.example.OrganizationService.contoller;

import com.example.OrganizationService.models.*;
import com.example.OrganizationService.service.DogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/organization")
public class DogController {

    @Autowired
    private DogService dogService;

    private Integer resolveOrgId(HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org != null) return org.getId();
        User user = (User) session.getAttribute("user");
        if (user != null) return user.getOrganizationId();
        return null;
    }

    // GET /organization/dogs
    @GetMapping("/dogs")
    public ResponseEntity<?> getAllDogs(HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        return ResponseEntity.ok(dogService.getByOrganization(orgId));
    }

    // GET /organization/dog/{id}
    @GetMapping("/dog/{id}")
    public ResponseEntity<?> getDog(@PathVariable int id, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.ok(dogService.getByIdAndOrganization(id, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Dog not found");
        }
    }

    // POST /organization/dog/add?bordetellaDate=2026-03-01&rabiesDate=2026-03-01
    // Body: { "name": "...", "breed": "...", "age": ..., "weight": ..., "image": "..." }
    @PostMapping("/dog/add")
    public ResponseEntity<?> addDog(@RequestBody Dog dog,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bordetellaDate,
                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rabiesDate,
                                    HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.status(201).body(dogService.create(dog, orgId, bordetellaDate, rabiesDate));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // DELETE /organization/dog/{id}
    // Organization session only.
    @DeleteMapping("/dog/{id}")
    public ResponseEntity<?> deleteDog(@PathVariable int id, HttpSession session) {
        Organization org = (Organization) session.getAttribute("organization");
        if (org == null) return ResponseEntity.status(403).body("Organization login required");
        try {
            dogService.delete(id, org.getId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ── Notes ─────────────────────────────────────────────────────────────────

    // POST /organization/dog/{id}/note
    @PostMapping("/dog/{id}/note")
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

    // PUT /organization/dog/{dogId}/note/{noteId}
    @PutMapping("/dog/{dogId}/note/{noteId}")
    public ResponseEntity<?> updateNote(@PathVariable int dogId,
                                        @PathVariable int noteId,
                                        @RequestBody Note note,
                                        HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.ok(dogService.updateNote(noteId, note, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // DELETE /organization/dog/{dogId}/note/{noteId}
    @DeleteMapping("/dog/{dogId}/note/{noteId}")
    public ResponseEntity<?> deleteNote(@PathVariable int dogId,
                                        @PathVariable int noteId,
                                        HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            dogService.deleteNote(noteId, orgId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ── Alerts ────────────────────────────────────────────────────────────────

    // POST /organization/dog/{id}/alert
    @PostMapping("/dog/{id}/alert")
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

    // PUT /organization/dog/{dogId}/alert/{alertId}
    @PutMapping("/dog/{dogId}/alert/{alertId}")
    public ResponseEntity<?> updateAlert(@PathVariable int dogId,
                                         @PathVariable int alertId,
                                         @RequestBody Alert alert,
                                         HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.ok(dogService.updateAlert(alertId, alert, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // DELETE /organization/dog/{dogId}/alert/{alertId}
    @DeleteMapping("/dog/{dogId}/alert/{alertId}")
    public ResponseEntity<?> deleteAlert(@PathVariable int dogId,
                                         @PathVariable int alertId,
                                         HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            dogService.deleteAlert(alertId, orgId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ── Likes ─────────────────────────────────────────────────────────────────

    // POST /organization/dog/{id}/like
    @PostMapping("/dog/{id}/like")
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

    // PUT /organization/dog/{dogId}/like/{likeId}
    @PutMapping("/dog/{dogId}/like/{likeId}")
    public ResponseEntity<?> updateLike(@PathVariable int dogId,
                                        @PathVariable int likeId,
                                        @RequestBody Like like,
                                        HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.ok(dogService.updateLike(likeId, like, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // DELETE /organization/dog/{dogId}/like/{likeId}
    @DeleteMapping("/dog/{dogId}/like/{likeId}")
    public ResponseEntity<?> deleteLike(@PathVariable int dogId,
                                        @PathVariable int likeId,
                                        HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            dogService.deleteLike(likeId, orgId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // ── Vaccines ──────────────────────────────────────────────────────────────

    // GET /organization/dog/{id}/vaccines
    @GetMapping("/dog/{id}/vaccines")
    public ResponseEntity<?> getVaccines(@PathVariable int id, HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            return ResponseEntity.ok(dogService.getVaccinesByDog(id, orgId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // PUT /organization/dog/{dogId}/vaccine/{vaccineId}/renew
    @PutMapping("/dog/{dogId}/vaccine/{vaccineId}/renew")
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

    // POST /organization/dog/{id}/vaccine
    // Body: { "name": "...", "vaccinatedDate": "2026-04-01", "expirationDate": "2027-04-01" }
    @PostMapping("/dog/{id}/vaccine")
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

    // DELETE /organization/dog/{dogId}/vaccine/{vaccineId}
    // Custom vaccines only — standard vaccines (Bordetella, Rabies) cannot be deleted.
    @DeleteMapping("/dog/{dogId}/vaccine/{vaccineId}")
    public ResponseEntity<?> deleteVaccine(@PathVariable int dogId,
                                           @PathVariable int vaccineId,
                                           HttpSession session) {
        Integer orgId = resolveOrgId(session);
        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
        try {
            dogService.deleteVaccine(vaccineId, orgId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // PUT /organization/dog/{id}/owner
    // Body: { "name": "...", "email": "...", "phoneNumber": "..." }
    @PutMapping("/dog/{id}/owner")
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
}
