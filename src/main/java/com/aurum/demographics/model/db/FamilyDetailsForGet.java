package com.aurum.demographics.model.db;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "family_details")
@Setter
@Getter
public class FamilyDetailsForGet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String familyId;
    private String respondentName;
    private String mobileNumber;
    private int areaDetails;
    private String isDeleted;

    @Formula("(select count(*) from member_details m where m.family_id_ref = family_id)")
    private int memberCount;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areaDetails", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    DemographicDetail demographicDetail;

}
