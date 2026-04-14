package com.example.OrganizationService.service;

import com.example.OrganizationService.models.Dog;
import com.example.OrganizationService.models.Organization;
import com.example.OrganizationService.models.Owner;
import com.example.OrganizationService.repo.DogRepo;
import com.example.OrganizationService.repo.OrganizationRepo;
import com.example.OrganizationService.repo.OwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepo ownerRepo;

    @Autowired
    private DogRepo dogRepo;

    @Autowired
    private OrganizationRepo organizationRepo;

    public List<Owner> getByOrganization(int orgId) {
        return ownerRepo.findByOrganizationId(orgId);
    }

    public Owner addOwner(String name, String email, String phoneNumber) {
        Owner owner = new Owner();
        owner.setName(name);
        owner.setEmail(email);
        owner.setPhoneNumber(phoneNumber);
        return ownerRepo.save(owner);
    }

    @Transactional
    public Owner addDogToOwner(int ownerId, String dogName, String breed, int age, int weight, int orgId) {
        Owner owner = ownerRepo.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        Dog dog = new Dog();
        dog.setName(dogName);
        dog.setBreed(breed);
        dog.setAge(age);
        dog.setWeight(weight);
        dog.setOwner(owner);
        dog.setOrganization(org);
        dogRepo.save(dog);

        return ownerRepo.findById(ownerId).orElseThrow();
    }

    // Returns the updated owner, or null if the owner was deleted (last dog removed).
    @Transactional
    public Owner removeDogFromOwner(int ownerId, int dogId, int orgId) {
        Dog dog = dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));

        if (dog.getOwner().getId() != ownerId) {
            throw new RuntimeException("Dog does not belong to this owner");
        }

        dogRepo.delete(dog);

        List<Dog> remaining = dogRepo.findByOwner_IdAndOrganization_Id(ownerId, orgId);
        if (remaining.isEmpty()) {
            ownerRepo.deleteById(ownerId);
            return null;
        }

        return ownerRepo.findById(ownerId).orElseThrow();
    }
}