package com.aurum.demographics.model.db;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "family_details")
@Setter
@Getter
public class FamilyDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statusOfHouse", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    StatusOfHouse statusOfHouseDetails;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeOfHouse", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    TypeOfHouse typeOfHouseDetails;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdBy", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    User userDetails;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "areaDetails", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    DemographicDetail demographicDetail;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "familyIdRef", referencedColumnName="familyId", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    List<MemberDetail> memberDetail;
}
