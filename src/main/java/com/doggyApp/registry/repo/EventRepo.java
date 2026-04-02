package com.doggyApp.registry.repo;

import com.doggyApp.registry.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {

    // Events belonging to a specific user (employee view)
    List<Event> findByUsers_Id(int userId);

    // All events in an organization, traversing user → organization (org admin view)
    @Query("SELECT e FROM Event e WHERE e.users.organization.id = :orgId")
    List<Event> findByOrganizationId(@Param("orgId") int orgId);
}