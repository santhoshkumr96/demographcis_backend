package com.aurum.demographics.repo;

import com.aurum.demographics.model.db.AnnuaIncome;
import com.aurum.demographics.model.db.Education;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnnualIncomeRepo extends CrudRepository<AnnuaIncome, Long> {
}