package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.Gender;
import com.aurum.demographics.model.db.StatusOfHouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GenderRepo extends CrudRepository<Gender, Long> {
}