package com.aurum.demographics.contorller;

import com.aurum.demographics.model.api.DeleteRequest;
import com.aurum.demographics.model.api.PaginationRequest;
import com.aurum.demographics.model.db.*;
import com.aurum.demographics.repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javah.Gen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class DemographicController {

    @Autowired
    DemograhicDetailRepository demograhicDetailRepository;

    @Autowired
    FamilyDetailRepository familyDetailRepository;

    @Autowired
    TypeOfHouseRepo typeOfHouseRepo;

    @Autowired
    StatusOfHouseRepo statusOfHouseRepo;

    @Autowired
    GenderRepo genderRepo;

    @Autowired
    MemberDetailsRepo memberDetailsRepo;

    @GetMapping("/getAreaDetails")
//    @PreAuthorize("hasRole('USER')")
    public Object getAreaDetails(){
        return demograhicDetailRepository.findAll();
    }

    @GetMapping("/getTypeOfHouseDetails")
//    @PreAuthorize("hasRole('USER')")
    public Object getTypeOFHouse(){
        return typeOfHouseRepo.findAll();
    }

    @GetMapping("/getStatusOfHouseDetails")
//    @PreAuthorize("hasRole('USER')")
    public Object getStatusOfHouse(){
        return statusOfHouseRepo.findAll();
    }

    @PostMapping("/getFamilyDetails")
//    @PreAuthorize("hasRole('USER')")
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

    @PostMapping("/getFamilyDetail")
//    @PreAuthorize("hasRole('USER')")
    public Object getFamilyDetail(@RequestParam String id) throws Exception {
       Optional<FamilyDetails> familyDetails = familyDetailRepository.findById(Long.parseLong(id));
       if(familyDetails.isPresent()){
           return familyDetails.get();
       }else {
           throw new Exception("Not able get family, contact admin");
       }
    }

    @PostMapping("/saveFamily")
//    @PreAuthorize("hasRole('USER')")
    public Long getFamilyDetail(@RequestBody FamilyDetails familyDetails) throws Exception {
        familyDetails.setIsDeleted("N");

        if (familyDetails.getStatusOfHouse() == 0){
             List<StatusOfHouse> houses = (List<StatusOfHouse>) statusOfHouseRepo.findAll();
             familyDetails.setStatusOfHouse((int) (long) houses.get(0).getId());
        }

        if (familyDetails.getTypeOfHouse() == 0){
            List<TypeOfHouse> type = (List<TypeOfHouse>) typeOfHouseRepo.findAll();
            familyDetails.setTypeOfHouse((int) (long) type.get(0).getId());
        }

        Long generatedId = familyDetailRepository.save(familyDetails).getId();
        return generatedId;
    }

    @PostMapping("/deleteFamily")
//    @PreAuthorize("hasRole('USER')")
    public void deleteFamily(@RequestBody DeleteRequest delete) throws Exception {
        Optional<FamilyDetails> familyDetails = familyDetailRepository.findById((long) delete.getId());
        if(familyDetails.isPresent()){
            FamilyDetails familyDetails1 = familyDetails.get();
            familyDetails1.setIsDeleted("Y");
            familyDetails1.setDeletedBy(delete.getUserId());
            familyDetailRepository.save(familyDetails1);
        } else {
            throw new Exception("Not able to delete, contact admin");
        }

    }

    @PostMapping("/saveMember")
//    @PreAuthorize("hasRole('USER')")
    public MemberDetail saveMemberDetail(@RequestBody MemberDetail memberDetail) throws Exception {

        memberDetail.setIsDeleted("N");
        if (memberDetail.getGender() == 0 ){
            List<Gender> gender = (List<Gender>) genderRepo.findAll();
            memberDetail.setGender((int)(long)gender.get(0).getId());
        }

        return memberDetailsRepo.save(memberDetail);
    }

    @PostMapping("/deleteMember")
//    @PreAuthorize("hasRole('USER')")
    public String deleteMember(@RequestBody DeleteRequest delete) throws Exception {
        Optional<MemberDetail> memberDetails = memberDetailsRepo.findById((long) delete.getId());
        if(memberDetails.isPresent()){
            MemberDetail memberDetail = memberDetails.get();
            memberDetail.setIsDeleted("Y");
            memberDetail.setDeletedBy(delete.getUserId());
            return memberDetailsRepo.save(memberDetail).getFamilyIdRef();
        } else {
            throw new Exception("Not able to delete, contact admin");
        }

    }

}
