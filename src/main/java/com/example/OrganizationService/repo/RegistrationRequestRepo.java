package com.example.OrganizationService.repo;

import com.example.OrganizationService.models.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRequestRepo extends JpaRepository<RegistrationRequest, Integer> {

    // Pending requests waiting for this org to accept or reject
    List<RegistrationRequest> findByOrganizationIdAndStatus(int organizationId, RegistrationRequest.Status status);
}
