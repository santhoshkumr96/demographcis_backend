package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.BloodGroup;
import com.aurum.demographics.model.db.MaritalStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BloodGroupRepo extends CrudRepository<BloodGroup, Long> {
}