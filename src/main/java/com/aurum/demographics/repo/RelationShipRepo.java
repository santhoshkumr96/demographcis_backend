package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.Gender;
import com.aurum.demographics.model.db.RelationShip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RelationShipRepo extends CrudRepository<RelationShip, Long> {
}