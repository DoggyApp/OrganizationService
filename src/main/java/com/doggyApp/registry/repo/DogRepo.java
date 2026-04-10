package com.doggyApp.registry.repo;

import com.doggyApp.registry.models.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DogRepo extends JpaRepository<Dog, Integer> {
    List<Dog> findByOrganizationId(int organizationId);
    Optional<Dog> findByIdAndOrganizationId(int dogId, int organizationId);
    List<Dog> findByOwner_IdAndOrganization_Id(int ownerId, int orgId);
}