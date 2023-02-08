package com.aurum.demographics.model.db;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "demographic_details_audit")
@Setter
@Getter
public class DemographicDetailAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int areaPreviousId;
    private String familyId;
    private int created_by;

}
