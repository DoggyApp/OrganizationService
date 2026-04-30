package com.example.OrganizationService.service;

import com.example.OrganizationService.models.*;
import com.example.OrganizationService.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationRequestService {

    @Autowired
    private RegistrationRequestRepo registrationRequestRepo;

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private DogRepo dogRepo;

    public List<RegistrationRequest> getPendingRequests(int orgId) {
        return registrationRequestRepo.findByOrganizationIdAndStatus(orgId, RegistrationRequest.Status.PENDING);
    }

    public void acceptRequest(int requestId, int orgId) {
        RegistrationRequest req = registrationRequestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (req.getOrganization().getId() != orgId) {
            throw new SecurityException("Not authorized");
        }

        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        Dog dog = dogRepo.findById(req.getDog().getId())
                .orElseThrow(() -> new RuntimeException("Dog not found"));

        org.getDogs().add(dog);
        organizationRepo.save(org);

        req.setStatus(RegistrationRequest.Status.ACCEPTED);
        registrationRequestRepo.save(req);
    }

    public void rejectRequest(int requestId, int orgId) {
        RegistrationRequest req = registrationRequestRepo.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (req.getOrganization().getId() != orgId) {
            throw new SecurityException("Not authorized");
        }

        req.setStatus(RegistrationRequest.Status.REJECTED);
        registrationRequestRepo.save(req);
    }
}
