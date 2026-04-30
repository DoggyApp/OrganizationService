package com.example.OrganizationService.repo;

import com.example.OrganizationService.models.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DogRepo extends JpaRepository<Dog, Integer> {

    @Query("SELECT d FROM Dog d JOIN d.organizations o WHERE o.id = :orgId")
    List<Dog> findByOrganizationId(@Param("orgId") int orgId);

    @Query("SELECT d FROM Dog d JOIN d.organizations o WHERE d.id = :dogId AND o.id = :orgId")
    Optional<Dog> findByIdAndOrganizationId(@Param("dogId") int dogId, @Param("orgId") int orgId);
}