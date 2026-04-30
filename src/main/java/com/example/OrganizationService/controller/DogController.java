package com.example.OrganizationService.controller;

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


//    // PUT /organization/dog/{id}/owner
//    // Body: { "name": "...", "email": "...", "phoneNumber": "..." }
//    @PutMapping("/dog/{id}/owner")
//    public ResponseEntity<?> setOwner(@PathVariable int id,
//                                      @RequestBody Owner owner,
//                                      HttpSession session) {
//        Integer orgId = resolveOrgId(session);
//        if (orgId == null) return ResponseEntity.status(401).body("Not logged in");
//        try {
//            return ResponseEntity.ok(dogService.setOwner(id, owner, orgId));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(404).body(e.getMessage());
//        }
//    }
}
