package com.aurum.demographics.model.db;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
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
    private String occupation;
    private String smartphone;
    private String govtInsurance;
    private String privateInsurance;
    private String oldAgePension;
    private String widowedPension;
    private String retiredPerson;
    private String vaccination;
    private Timestamp doseOne;
    private Timestamp doseTwo;

    private Timestamp birthDate;
    private String smoking;
    private String drinking;
    private String tobacco;
    private String diabetes;
    private String bp;
    private String osteoporosis;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    Gender genderDetails;
}
