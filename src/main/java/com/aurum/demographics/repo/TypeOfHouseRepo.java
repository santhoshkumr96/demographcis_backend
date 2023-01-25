package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.DemographicDetail;
import com.aurum.demographics.model.db.TypeOfHouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TypeOfHouseRepo extends CrudRepository<TypeOfHouse, Long> {
}