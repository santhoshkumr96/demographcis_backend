package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.FamilyDetails;
import liquibase.pro.packaged.W;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FamilyDetailRepository extends PagingAndSortingRepository<FamilyDetails, Long> {
    Page<FamilyDetails> findAll(Pageable pageable);

    Page<FamilyDetails> findByFamilyIdContainingAndRespondentNameContainingAndMobileNumberContainingAndDemographicDetailVillageNameContainingAndIsDeleted(String familyId, String RespondentName,String MobileNumber, String villageName,String isDeleted, Pageable pageable);
}