package com.doggyApp.registry.service;

import com.doggyApp.registry.models.*;
import com.doggyApp.registry.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));
    }

    public Alert addAlert(int dogId, Alert alert, int orgId) {
        Dog dog = dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));
        alert.setDog(dog);
        return alertRepo.save(alert);
    }

    public Note addNote(int dogId, Note note, int orgId) {
        Dog dog = dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));
        note.setDog(dog);
        return noteRepo.save(note);
    }

    public Like addLike(int dogId, Like like, int orgId) {
        Dog dog = dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));
        like.setDog(dog);
        return likeRepo.save(like);
    }

    public Dog setOwner(int dogId, Owner owner, int orgId) {
        Dog dog = dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));
        Owner savedOwner = ownerRepo.save(owner);
        dog.setOwner(savedOwner);
        return dogRepo.save(dog);
    }

    public Alert updateAlert(int alertId, Alert updates, int orgId) {
        Alert alert = alertRepo.findByIdAndOrganizationId(alertId, orgId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        if (updates.getAlert() != null) alert.setAlert(updates.getAlert());
        return alertRepo.save(alert);
    }

    public void deleteAlert(int alertId, int orgId) {
        Alert alert = alertRepo.findByIdAndOrganizationId(alertId, orgId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        alertRepo.delete(alert);
    }

    public Note updateNote(int noteId, Note updates, int orgId) {
        Note note = noteRepo.findByIdAndOrganizationId(noteId, orgId)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        if (updates.getNote() != null) note.setNote(updates.getNote());
        return noteRepo.save(note);
    }

    public void deleteNote(int noteId, int orgId) {
        Note note = noteRepo.findByIdAndOrganizationId(noteId, orgId)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        noteRepo.delete(note);
    }

    public Like updateLike(int likeId, Like updates, int orgId) {
        Like like = likeRepo.findByIdAndOrganizationId(likeId, orgId)
                .orElseThrow(() -> new RuntimeException("Like not found"));
        if (updates.getLike() != null) like.setLike(updates.getLike());
        return likeRepo.save(like);
    }

    public void deleteLike(int likeId, int orgId) {
        Like like = likeRepo.findByIdAndOrganizationId(likeId, orgId)
                .orElseThrow(() -> new RuntimeException("Like not found"));
        likeRepo.delete(like);
    }

    // Only the organization that owns the dog may delete it.
    public void delete(int dogId, int orgId) {
        Dog dog = dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));
        dogRepo.delete(dog);
    }

    // Callable by both org and user sessions — orgId is resolved from the session.
    // Bordetella and Rabies dates are required; expiration is auto-calculated from each.
    public Dog create(Dog dog, int orgId, LocalDate bordetellaDate, LocalDate rabiesDate) {
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        dog.setOrganization(org);
        Dog saved = dogRepo.save(dog);

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

    // Marks a standard vaccine as administered today and calculates the next renewal date.
    public Vaccine renewVaccine(int vaccineId, int orgId) {
        Vaccine vaccine = vaccineRepo.findByIdAndOrganizationId(vaccineId, orgId)
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));

        LocalDate today = LocalDate.now();
        int renewalMonths = vaccine.getName().equalsIgnoreCase("Rabies")
                ? RABIES_RENEWAL_MONTHS
                : BORDETELLA_RENEWAL_MONTHS;

        vaccine.setVaccinatedDate(today);
        vaccine.setExpirationDate(today.plusMonths(renewalMonths));
        return vaccineRepo.save(vaccine);
    }

    // Adds a custom vaccine with a manually provided expiration date.
    public Vaccine addCustomVaccine(int dogId, Vaccine vaccine, int orgId) {
        Dog dog = dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));
        vaccine.setStandard(false);
        vaccine.setDog(dog);
        return vaccineRepo.save(vaccine);
    }

    public List<Vaccine> getVaccinesByDog(int dogId, int orgId) {
        dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));
        return vaccineRepo.findByDogIdAndOrganizationId(dogId, orgId);
    }

    // Custom vaccines can be deleted; standard vaccines (Bordetella, Rabies) cannot.
    public void deleteVaccine(int vaccineId, int orgId) {
        Vaccine vaccine = vaccineRepo.findByIdAndOrganizationId(vaccineId, orgId)
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));
        if (vaccine.isStandard()) {
            throw new RuntimeException("Required vaccines cannot be deleted");
        }
        vaccineRepo.delete(vaccine);
    }
}