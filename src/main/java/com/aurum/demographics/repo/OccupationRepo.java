package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.BloodGroup;
import com.aurum.demographics.model.db.Occupation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OccupationRepo extends CrudRepository<Occupation, Long> {
}