package com.aurum.demographics.model.db;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
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
    private String livestockDetails;
    private String toiletFacilityAtHome;
    private int createdBy;
    private String isDeleted;
    private String deletedBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statusOfHouse", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    StatusOfHouse statusOfHouseDetails;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeOfHouse", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    TypeOfHouse typeOfHouseDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    User userDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areaDetails", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    DemographicDetail demographicDetail;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "familyIdRef", referencedColumnName="familyId", insertable = false, updatable = false)
    @Fetch(FetchMode.SELECT)
    List<MemberDetail> memberDetail;
}
