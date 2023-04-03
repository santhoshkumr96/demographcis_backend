package com.aurum.demographics.model.db;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "member_details_audit")
@Setter
@Getter
public class MemberDetailAudit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refId;
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

    private String annualIncomeString;
    private String tmhId;
    private String patientId;
    private int handicapType;

    private String permanentSterlization;
    private Date permanentSterlizationDate;
    private String tmpSterlization;
    private String tmpSterlizationType;

    private Date diabeticEnrolmentDate;
    private String  diabeticEnrollmentStatus ;
    private Date  diabeticEnrollmentEndDate;
}
