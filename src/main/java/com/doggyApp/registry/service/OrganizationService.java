package com.doggyApp.registry.service;

import com.doggyApp.registry.models.Organization;
import com.doggyApp.registry.repo.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepo organizationRepo;

    public Organization login(String email, String password) {
        Organization org = organizationRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!org.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        return org;
    }
}