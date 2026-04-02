package com.doggyApp.registry.repo;

import com.doggyApp.registry.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Integer> {
    Optional<Organization> findByEmail(String email);
}