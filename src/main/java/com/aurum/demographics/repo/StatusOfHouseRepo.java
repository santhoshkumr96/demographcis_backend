package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.StatusOfHouse;
import com.aurum.demographics.model.db.TypeOfHouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StatusOfHouseRepo extends CrudRepository<StatusOfHouse, Long> {
}