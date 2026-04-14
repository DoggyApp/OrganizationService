package com.example.OrganizationService.repo;

import com.example.OrganizationService.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Like, Integer> {

    @Query("SELECT l FROM Like l WHERE l.id = :id AND l.dog.organization.id = :orgId")
    Optional<Like> findByIdAndOrganizationId(@Param("id") int id, @Param("orgId") int orgId);
}