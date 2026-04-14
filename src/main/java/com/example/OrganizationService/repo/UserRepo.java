package com.example.OrganizationService.repo;

import com.example.OrganizationService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<User> findByIdAndOrganizationId(int id, int orgId);

    List<User> findByOrganizationId(int orgId);
}