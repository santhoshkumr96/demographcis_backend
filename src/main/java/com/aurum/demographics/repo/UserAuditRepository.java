package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.User;
import com.aurum.demographics.model.db.UserAuditTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserAuditRepository extends CrudRepository<UserAuditTable, Long> {

}