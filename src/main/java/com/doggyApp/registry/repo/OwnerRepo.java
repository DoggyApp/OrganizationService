package com.doggyApp.registry.repo;

import com.doggyApp.registry.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepo extends JpaRepository<Owner, Integer> {

    @Query("SELECT DISTINCT d.owner FROM Dog d WHERE d.organization.id = :orgId")
    List<Owner> findByOrganizationId(int orgId);
}