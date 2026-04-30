package com.example.OrganizationService.service;

import com.example.OrganizationService.models.Organization;
import com.example.OrganizationService.repo.OrganizationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional 
    public Organization register(Organization org) {
        if (organizationRepo.existsByEmail(org.getEmail())) {
            throw new IllegalStateException("Email already in use");
        }
        org.setPassword(passwordEncoder.encode(org.getPassword()));
        LocalDate today = LocalDate.now();
        org.setSubscriptionStart(today);
        org.setSubscriptionExpiration(today.plusYears(1));
        return organizationRepo.save(org);
    }

    @Transactional
    public Organization renew(int orgId) {
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new NoSuchElementException("Organization not found"));
        LocalDate today    = LocalDate.now();
        LocalDate baseDate = (org.getSubscriptionExpiration() != null && org.getSubscriptionExpiration().isAfter(today))
                ? org.getSubscriptionExpiration()
                : today;
        org.setSubscriptionStart(today);
        org.setSubscriptionExpiration(baseDate.plusYears(1));
        return organizationRepo.save(org);
    }

    @Transactional
    public Organization updateProfile(int orgId, String name, String email) {
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new NoSuchElementException("Organization not found"));
        if (!org.getEmail().equals(email) && organizationRepo.existsByEmail(email))
            throw new IllegalStateException("Email already in use");
        org.setName(name);
        org.setEmail(email);
        return organizationRepo.save(org);
    }

    @Transactional
    public void changePassword(int orgId, String oldPassword, String newPassword) {
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new NoSuchElementException("Organization not found"));
        if (!passwordEncoder.matches(oldPassword, org.getPassword()))
            throw new SecurityException("Current password is incorrect");
        org.setPassword(passwordEncoder.encode(newPassword));
        organizationRepo.save(org);
    }

    public Organization login(String email, String password) {
        Organization org = organizationRepo.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Invalid credentials"));
        if (!passwordEncoder.matches(password, org.getPassword()))
            throw new SecurityException("Invalid credentials");
        return org;
    }
}