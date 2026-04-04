package com.doggyApp.registry.repo;

import com.doggyApp.registry.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface EventRepo extends JpaRepository<Event, Integer> {

    // Events belonging to a specific user (employee view)
    List<Event> findByUsers_Id(int userId);

    // Events for a specific user, scoped to an org
    List<Event> findByUsers_IdAndUsers_OrganizationId(int userId, int orgId);

    // Events for a specific dog, scoped to an org
    List<Event> findByDogs_IdAndDogs_Organization_Id(int dogId, int orgId);

    // All events in an organization, traversing user → organization (org admin view)
    @Query("SELECT e FROM Event e WHERE e.users.organization.id = :orgId")
    List<Event> findByOrganizationId(@Param("orgId") int orgId);

    // Single event scoped to an org — used before allowing user/dog assignment or org edits
    Optional<Event> findByIdAndUsers_OrganizationId(int id, int orgId);

    // Single event scoped to a specific user — used before allowing the creator to edit
    Optional<Event> findByIdAndUsers_Id(int id, int userId);

    // Edit authorization — only matches if this user is the original creator
    Optional<Event> findByIdAndCreator_Id(int id, int creatorId);

    // Cascade delete — removes all events created by this user
    void deleteByCreator_Id(int creatorId);

    // All events at a specific location, scoped to the requesting org
    List<Event> findByLocation_IdAndLocation_OrgId(int locationId, int orgId);

    // Cascade delete — nullifies the assigned-user field on events where this user was the assignee but not the creator
    @Modifying
    @Query("UPDATE Event e SET e.users = null WHERE e.users.id = :userId")
    void clearAssignedUser(@Param("userId") int userId);
}