package com.doggyApp.registry.service;

import com.doggyApp.registry.models.Organization;
import com.doggyApp.registry.repo.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Organization register(Organization org) {
        if (organizationRepo.findByEmail(org.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        org.setPassword(passwordEncoder.encode(org.getPassword()));
        LocalDate today = LocalDate.now();
        org.setSubscriptionStart(today);
        org.setSubscriptionExpiration(today.plusYears(1));
        return organizationRepo.save(org);
    }

    public Organization renew(int orgId) {
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        LocalDate today = LocalDate.now();
        LocalDate baseDate = (org.getSubscriptionExpiration() != null && org.getSubscriptionExpiration().isAfter(today))
                ? org.getSubscriptionExpiration()
                : today;
        org.setSubscriptionStart(today);
        org.setSubscriptionExpiration(baseDate.plusYears(1));
        return organizationRepo.save(org);
    }

    public Organization updateProfile(int orgId, String name, String email) {
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        if (!org.getEmail().equals(email) && organizationRepo.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        org.setName(name);
        org.setEmail(email);
        return organizationRepo.save(org);
    }

    public void changePassword(int orgId, String oldPassword, String newPassword) {
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        if (!passwordEncoder.matches(oldPassword, org.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        org.setPassword(passwordEncoder.encode(newPassword));
        organizationRepo.save(org);
    }

    public Organization login(String email, String password) {
        Organization org = organizationRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, org.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return org;
    }
}