package com.aurum.demographics.contorller;

import com.aurum.demographics.model.api.DeleteRequest;
import com.aurum.demographics.model.api.PaginationRequest;
import com.aurum.demographics.repo.DemograhicDetailRepository;
import com.aurum.demographics.repo.FamilyDetailRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class DemographicController {

    @Autowired
    DemograhicDetailRepository demograhicDetailRepository;

    @Autowired
    FamilyDetailRepository familyDetailRepository;

    @GetMapping("/getAreaDetails")
    @PreAuthorize("hasRole('USER')")
    public Object getAreaDetails(){
        return demograhicDetailRepository.findAll();
    }

    @PostMapping("/getFamilyDetails")
    @PreAuthorize("hasRole('USER')")
    public Object getFamilyDetails(@RequestBody PaginationRequest pagination){
        Pageable page =
                PageRequest.of(pagination.getPageNumber(), pagination.getNumberOfRows(), Sort.by("id"));
        return familyDetailRepository.findByFamilyIdContainingAndRespondentNameContainingAndMobileNumberContainingAndDemographicDetailVillageNameContainingAndIsDeleted(
                pagination.getFamilyId(),
                pagination.getRespondentName(),
                pagination.getMobileNumber(),
                pagination.getVillageName(),
                "N",
                page
        );
    }

    @PostMapping("/deleteFamily")
    @PreAuthorize("hasRole('USER')")
    public void deleteFamily(@RequestBody DeleteRequest delete) throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(delete));
    }

}
