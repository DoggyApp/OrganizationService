package com.doggyApp.registry.service;

import com.doggyApp.registry.models.Dog;
import com.doggyApp.registry.repo.DogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService {

    @Autowired
    private DogRepo dogRepo;

    public List<Dog> getByOrganization(int orgId) {
        return dogRepo.findByOrganizationId(orgId);
    }

    public Dog getByIdAndOrganization(int dogId, int orgId) {
        return dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));
    }
}