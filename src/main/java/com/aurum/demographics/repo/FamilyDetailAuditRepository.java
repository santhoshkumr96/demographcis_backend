package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.FamilyDetails;
import com.aurum.demographics.model.db.FamilyDetailsAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FamilyDetailAuditRepository extends PagingAndSortingRepository<FamilyDetailsAudit, Long> {
}