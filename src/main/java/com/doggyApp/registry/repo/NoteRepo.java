package com.doggyApp.registry.repo;

import com.doggyApp.registry.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepo extends JpaRepository<Note, Integer> {

    @Query("SELECT n FROM Note n WHERE n.id = :id AND n.dog.organization.id = :orgId")
    Optional<Note> findByIdAndOrganizationId(@Param("id") int id, @Param("orgId") int orgId);
}