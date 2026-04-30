package com.example.OrganizationService.repo;

import com.example.OrganizationService.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepo extends JpaRepository<Owner, Integer> {

    @Query("SELECT DISTINCT d.owner FROM Dog d JOIN d.organizations o WHERE o.id = :orgId AND d.owner IS NOT NULL")
    List<Owner> findByOrganizationId(@Param("orgId") int orgId);
}