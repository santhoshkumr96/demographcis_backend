package com.aurum.demographics.model.db;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "member_details")
@Setter
@Getter
public class MemberDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String familyIdRef;
    private String memberName;
    private int gender;
    private Integer createdBy;
    private Integer updatedBy;
    private Integer deletedBy;
    private String isDeleted;
    private String aadharNumber;
    private String mobileNumber;

    private String email;
    private String physicallyChallenged;
    private String physicallyChallengedDetails;
    private int occupation;
    private String smartphone;
    private String govtInsurance;
    private String privateInsurance;
    private String oldAgePension;
    private String widowedPension;
    private String retiredPerson;
    private String vaccination;
    private Timestamp doseOne;
    private Timestamp doseTwo;

    private Date birthDate;
    private String smoking;
    private String drinking;
    private String tobacco;
    private String diabetes;
    private String bp;
    private String osteoporosis;

    private String isOsteoporosisScan;
    private Timestamp osteoporosisScanOne;
    private Timestamp osteoporosisScanTwo;
    private Timestamp deceasedDate;
    private String isDeceased;

    private String breastCancer;
    private String uterusCancer;
    private Timestamp uterusCancerScan;
    private Timestamp breastCancerScan;
    private String oralCancer;
    private String obesity;
    private String heartDiseases;
    private String lungRelatedDiseases;
    private String asthma;
    private String jointPain;
    private String otherDiseases;


    private int community;

    private String imageLocation;


    private int relationship;
    private int maritalStatus;
    private int bloodGroup;
    private int educationQualification;
    private int annualIncome;
    private int handicapType;

    private int height;

    private int weight;

    private String annualIncomeString;
    private String tmhId;
    private String patientId;

    private String permanentSterlization;
    private Date permanentSterlizationDate;
    private String tmpSterlization;
    private String tmpSterlizationType;

    private Date diabeticEnrolmentDate;
    private String  diabeticEnrollmentStatus ;
    private Date  diabeticEnrollmentEndDate;

    private String diabeticPackage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    Gender genderDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relationship", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    RelationShip relationshipDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maritalStatus", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    MaritalStatus maritalStatusDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bloodGroup", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    BloodGroup bloodGroupDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "educationQualification", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    Education educationQualificationDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annualIncome", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    AnnuaIncome annualIncomeDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    CommunityDetail communityDetail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    Occupation occupationDetail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handicapType", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    HandicapType handicapTypeDetail;
}
