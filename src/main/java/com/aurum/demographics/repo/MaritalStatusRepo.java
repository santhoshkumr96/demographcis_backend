package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.MaritalStatus;
import com.aurum.demographics.model.db.RelationShip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MaritalStatusRepo extends CrudRepository<MaritalStatus, Long> {
}