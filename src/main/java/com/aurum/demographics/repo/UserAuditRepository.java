package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.UserAudit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAuditRepository extends CrudRepository<UserAudit, Long> {

}