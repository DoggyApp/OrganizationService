package com.doggyApp.registry.service;

import com.doggyApp.registry.models.Organization;
import com.doggyApp.registry.repo.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Organization login(String email, String password) {
        Organization org = organizationRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, org.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return org;
    }
}