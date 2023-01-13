package com.aurum.demographics.model.db;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

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
    private int createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    Gender genderDetails;
}
