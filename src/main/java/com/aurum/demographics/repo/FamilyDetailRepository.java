package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.FamilyDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FamilyDetailRepository extends PagingAndSortingRepository<FamilyDetails, Long> {
    Page<FamilyDetails> findAll(Pageable pageable);
}