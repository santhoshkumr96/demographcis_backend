package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.BloodGroup;
import com.aurum.demographics.model.db.Education;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EducationQualificationRepo extends CrudRepository<Education, Long> {
}