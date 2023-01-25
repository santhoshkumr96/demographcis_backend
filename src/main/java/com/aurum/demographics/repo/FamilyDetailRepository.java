package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.FamilyDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FamilyDetailRepository extends PagingAndSortingRepository<FamilyDetails, Long> {
    Page<FamilyDetails> findAll(Pageable pageable);

    Page<FamilyDetails> findByFamilyIdContainingAndRespondentNameContainingAndMobileNumberContainingAndDemographicDetailVillageNameContainingAndIsDeleted(String familyId, String RespondentName,String MobileNumber, String villageName,String isDeleted, Pageable pageable);

    Optional<FamilyDetails> findById(Long id);
}