package com.doggyApp.registry.service;

import com.doggyApp.registry.models.Owner;
import com.doggyApp.registry.repo.OwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepo ownerRepo;

    public List<Owner> getByOrganization(int orgId) {
        return ownerRepo.findByOrganizationId(orgId);
    }
}