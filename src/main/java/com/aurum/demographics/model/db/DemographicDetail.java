package com.aurum.demographics.model.db;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "demographic_details")
@Setter
@Getter
public class DemographicDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String district;
    private String taluk;
    private String blockName;
    private String panchayat;
    private int areaCode;
    private String villageCode;
    private String villageName;
    private String streetName;
}
