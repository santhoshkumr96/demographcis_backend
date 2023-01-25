package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.MemberDetail;
import com.aurum.demographics.model.db.StatusOfHouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberDetailsRepo extends CrudRepository<MemberDetail, Long> {
}