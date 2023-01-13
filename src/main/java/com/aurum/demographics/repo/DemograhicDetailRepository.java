package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.DemographicDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DemograhicDetailRepository extends CrudRepository<DemographicDetail, Long> {
}