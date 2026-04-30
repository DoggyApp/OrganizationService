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

}