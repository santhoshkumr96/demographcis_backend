package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.DemographicDetail;
import com.aurum.demographics.model.db.DemographicDetailAudit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DemograhicDetailAuditRepository extends CrudRepository<DemographicDetailAudit, Long> {
}