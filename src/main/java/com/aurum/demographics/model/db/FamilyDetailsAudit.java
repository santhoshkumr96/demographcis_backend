package com.aurum.demographics.model.db;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "family_details_audit")
@Setter
@Getter
public class FamilyDetailsAudit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refId;
    private Long id;
    private String familyId;
    private int areaDetails;
    private String doorNo;
    private String respondentName;
    private String mobileNumber;
    private int statusOfHouse;
    private int typeOfHouse;
    private String wetLandInAcres;
    private String dryLandInAcres;
    private String motorVechicles;
    private int oneWheeler;
    private int twoWheeler;
    private int threeWheeler;
    private int fourWheeler;
    private int noOtherVechicles;
    private String otherVechiclesDetails;
    private String livestockDetails;
    private int hen;
    private int cow;
    private int pig;
    private int buffalo;
    private int goat;
    private int noOtherLivestock;
    private String otherLivestockDetails;
    private String toiletFacilityAtHome;
    private Integer createdBy;
    private Integer updatedBy;
    private Integer deletedBy;
    private String isDeleted;

}
