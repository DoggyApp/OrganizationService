package com.example.OrganizationService.repo;

import com.example.OrganizationService.models.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlertRepo extends JpaRepository<Alert, Integer> {

    @Query("SELECT a FROM Alert a WHERE a.id = :id AND a.dog.organization.id = :orgId")
    Optional<Alert> findByIdAndOrganizationId(@Param("id") int id, @Param("orgId") int orgId);
}