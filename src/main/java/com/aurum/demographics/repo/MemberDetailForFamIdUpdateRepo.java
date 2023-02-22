package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.FamilyDetailsForGet;
import com.aurum.demographics.model.db.MemberDetailForFamIdUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberDetailForFamIdUpdateRepo extends PagingAndSortingRepository<MemberDetailForFamIdUpdate, Long> {
    List<MemberDetailForFamIdUpdate> findByFamilyIdRef(String familyId);
}