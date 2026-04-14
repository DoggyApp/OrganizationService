package com.example.OrganizationService.repo;

import com.example.OrganizationService.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Integer> {
    Optional<Organization> findByEmail(String email);
    boolean existsByEmail(String email);
}