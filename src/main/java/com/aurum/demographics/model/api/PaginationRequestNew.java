package com.aurum.demographics.model.api;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaginationRequestNew {
    private Integer numberOfRows;
    private Integer pageNumber;
    private String familyId;
    private String respondentName;

    private String memberName;
    private String mobileNumber;
    private String villageName;
}
