package com.aurum.demographics.repo;

import java.util.Optional;

import com.aurum.demographics.enums.ERole;
import com.aurum.demographics.model.db.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}