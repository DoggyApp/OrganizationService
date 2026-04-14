package com.example.OrganizationService.repo;

import com.example.OrganizationService.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepo extends JpaRepository<Location, Integer> {

    List<Location> findByOrgId(int orgId);

    Optional<Location> findByIdAndOrgId(int id, int orgId);
}