package com.example.OrganizationService.service;

import com.example.OrganizationService.models.*;
import com.example.OrganizationService.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DogService {

    @Autowired
    private DogRepo dogRepo;

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private AlertRepo alertRepo;

    @Autowired
    private NoteRepo noteRepo;

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private OwnerRepo ownerRepo;

    @Autowired
    private VaccineRepo vaccineRepo;

    // Renewal intervals in months — based on standard veterinary guidelines
    private static final int BORDETELLA_RENEWAL_MONTHS = 12;
    private static final int RABIES_RENEWAL_MONTHS     = 12;


    public List<Dog> getByOrganization(int orgId) {
        return dogRepo.findByOrganizationId(orgId);
    }

    public Dog getByIdAndOrganization(int dogId, int orgId) {
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        return org.getDogs().stream()
                .filter(d -> d.getId() == dogId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Dog not found"));
    }

    // Removes the dog from this org's client list only — never deletes the dog entity.
    public void delete(int dogId, int orgId) {
        dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        org.getDogs().removeIf(d -> d.getId() == dogId);
        organizationRepo.save(org);
    }

    @Transactional
    // Callable by both org and user sessions — orgId is resolved from the session.
    // Bordetella and Rabies dates are required; expiration is auto-calculated from each.
    public Dog create(Dog dog, int orgId, LocalDate bordetellaDate, LocalDate rabiesDate) {
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        Dog saved = dogRepo.save(dog);
        org.getDogs().add(saved);
        organizationRepo.save(org);

        Vaccine bordetella = new Vaccine();
        bordetella.setName("Bordetella");
        bordetella.setStandard(true);
        bordetella.setVaccinatedDate(bordetellaDate);
        bordetella.setExpirationDate(bordetellaDate.plusMonths(BORDETELLA_RENEWAL_MONTHS));
        bordetella.setDog(saved);
        vaccineRepo.save(bordetella);

        Vaccine rabies = new Vaccine();
        rabies.setName("Rabies");
        rabies.setStandard(true);
        rabies.setVaccinatedDate(rabiesDate);
        rabies.setExpirationDate(rabiesDate.plusMonths(RABIES_RENEWAL_MONTHS));
        rabies.setDog(saved);
        vaccineRepo.save(rabies);

        return saved;
    }

}