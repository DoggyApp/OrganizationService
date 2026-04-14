package com.example.OrganizationService.repo;

import com.example.OrganizationService.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface EventRepo extends JpaRepository<Event, Integer> {

    // Events where this user is an attendee (employee view)
    List<Event> findByAttendees_Id(int userId);

    // Events where this user is an attendee, scoped to an org
    List<Event> findByAttendees_IdAndAttendees_OrganizationId(int userId, int orgId);

    // Events for a specific dog, scoped to an org
    List<Event> findByDogs_IdAndDogs_Organization_Id(int dogId, int orgId);

    // All events in an organization, scoped through the creator (org admin view)
    @Query("SELECT DISTINCT e FROM Event e WHERE e.creator.organizationId = :orgId")
    List<Event> findByOrganizationId(@Param("orgId") int orgId);

    // Single event scoped to an org via the creator — used before allowing assignment or edits
    Optional<Event> findByIdAndCreator_OrganizationId(int id, int orgId);

    // Edit authorization — only matches if this user is the original creator
    Optional<Event> findByIdAndCreator_Id(int id, int creatorId);

    // Cascade delete — removes all events created by this user
    void deleteByCreator_Id(int creatorId);

    // All events at a specific location, scoped to the requesting org
    List<Event> findByLocation_IdAndLocation_OrgId(int locationId, int orgId);

    // Removes a user from all event attendee lists (called before deleting the user)
    @Modifying
    @Query(value = "DELETE FROM event_attendees WHERE user_id = :userId", nativeQuery = true)
    void clearAssignedUser(@Param("userId") int userId);

    // Nullify location on events before a location is deleted
    @Modifying
    @Query("UPDATE Event e SET e.location = null WHERE e.location.id = :locationId")
    void clearLocation(@Param("locationId") int locationId);
}