package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.CommunityDetail;
import com.aurum.demographics.model.db.DemographicDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommunityDetailRepository extends CrudRepository<CommunityDetail, Long> {
}