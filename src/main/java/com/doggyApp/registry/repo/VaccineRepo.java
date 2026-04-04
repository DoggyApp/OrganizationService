package com.doggyApp.registry.repo;

import com.doggyApp.registry.models.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Integer> {

    @Query("SELECT v FROM Vaccine v WHERE v.id = :id AND v.dog.organization.id = :orgId")
    Optional<Vaccine> findByIdAndOrganizationId(@Param("id") int id, @Param("orgId") int orgId);

    @Query("SELECT v FROM Vaccine v WHERE v.dog.id = :dogId AND v.dog.organization.id = :orgId")
    List<Vaccine> findByDogIdAndOrganizationId(@Param("dogId") int dogId, @Param("orgId") int orgId);
}