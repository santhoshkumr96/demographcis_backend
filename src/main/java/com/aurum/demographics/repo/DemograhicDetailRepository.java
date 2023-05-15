package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.DemographicDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DemograhicDetailRepository extends CrudRepository<DemographicDetail, Long> {
    List<DemographicDetail> findByIsDeleted(String deleted);
}