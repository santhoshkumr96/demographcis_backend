package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.FamilyDetails;
import com.aurum.demographics.model.db.FamilyDetailsForGet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FamilyDetailRepositoryForGet extends PagingAndSortingRepository<FamilyDetailsForGet, Long> {

    Page<FamilyDetailsForGet> findByFamilyIdContainingAndRespondentNameContainingAndMobileNumberContainingAndDemographicDetailVillageNameContainingAndIsDeleted(String familyId, String RespondentName,String MobileNumber, String villageName,String isDeleted, Pageable pageable);

    Page<FamilyDetailsForGet> findByFamilyIdContainingAndRespondentNameContainingAndMobileNumberContainingAndMemberDetailMemberNameContainingAndDemographicDetailVillageNameContainingAndIsDeleted(String familyId, String RespondentName,String MobileNumber,String memberName, String villageName,String isDeleted, Pageable pageable);

    List<FamilyDetailsForGet> findByFamilyIdContainingOrderByFamilyIdDesc(String areaCode);
}