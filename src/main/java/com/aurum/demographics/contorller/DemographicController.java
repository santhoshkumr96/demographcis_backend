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

import java.lang.reflect.AnnotatedArrayType;
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

    @Autowired
    AnnualIncomeRepo annualIncomeRepo;

    @Autowired
    BloodGroupRepo bloodGroupRepo;

    @Autowired
    EducationQualificationRepo educationQualificationRepo;

    @Autowired
    MaritalStatusRepo maritalStatusRepo;

    @Autowired
    RelationShipRepo relationShipRepo;

    @Autowired
    DemograhicDetailAuditRepository demograhicDetailAuditRepository;

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

    @GetMapping("/getGender")
//    @PreAuthorize("hasRole('USER')")
    public Object getGender(){
        return genderRepo.findAll();
    }

    @GetMapping("/getRelationship")
//    @PreAuthorize("hasRole('USER')")
    public Object getRelationship(){
        return relationShipRepo.findAll();
    }

    @GetMapping("/getEducationQualification")
//    @PreAuthorize("hasRole('USER')")
    public Object getEducationQualification(){
        return educationQualificationRepo.findAll();
    }

    @GetMapping("/getBloodGroup")
//    @PreAuthorize("hasRole('USER')")
    public Object getBloodGroup(){
        return bloodGroupRepo.findAll();
    }

    @GetMapping("/getMaritalStatus")
//    @PreAuthorize("hasRole('USER')")
    public Object getMaritalStatus(){
        return maritalStatusRepo.findAll();
    }

    @GetMapping("/getAnnualIncome")
//    @PreAuthorize("hasRole('USER')")
    public Object getAnnualIncome(){
        return annualIncomeRepo.findAll();
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

        if (familyDetails.getAreaDetails() == 0){
            List<DemographicDetail> areas = (List<DemographicDetail>) demograhicDetailRepository.findAll();
            familyDetails.setAreaDetails((int)(long) areas.get(0).getId());
        }

        if(!Objects.isNull(familyDetails.getId())){
           FamilyDetails oldDetails = familyDetailRepository.findById(familyDetails.getId()).get();
           if(oldDetails.getAreaDetails() != familyDetails.getAreaDetails()){
               DemographicDetailAudit demographicDetailAudit = new DemographicDetailAudit();
               demographicDetailAudit.setCreated_by(familyDetails.getUpdatedBy());
               demographicDetailAudit.setAreaPreviousId(oldDetails.getAreaDetails());
               demographicDetailAudit.setFamilyId(familyDetails.getFamilyId());
               demograhicDetailAuditRepository.save(demographicDetailAudit);
               log.info("area is changed");
           }
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

        if (memberDetail.getRelationship() == 0 ){
            List<RelationShip> relationShips = (List<RelationShip>) relationShipRepo.findAll();
            memberDetail.setRelationship((int)(long)relationShips.get(0).getId());
        }

        if (memberDetail.getBloodGroup() == 0 ){
            List<BloodGroup> bloodGroup = (List<BloodGroup>) bloodGroupRepo.findAll();
            memberDetail.setBloodGroup((int)(long)bloodGroup.get(0).getId());
        }

        if (memberDetail.getEducationQualification() == 0 ){
            List<Education> educations = (List<Education>) educationQualificationRepo.findAll();
            memberDetail.setEducationQualification((int)(long)educations.get(0).getId());
        }

        if (memberDetail.getAnnualIncome() == 0 ){
            List<AnnuaIncome> annualIncomes = (List<AnnuaIncome>) annualIncomeRepo.findAll();
            memberDetail.setAnnualIncome((int)(long)annualIncomes.get(0).getId());
        }

        if (memberDetail.getMaritalStatus() == 0 ){
            List<MaritalStatus> maritalStatuses = (List<MaritalStatus>) maritalStatusRepo.findAll();
            memberDetail.setMaritalStatus((int)(long)maritalStatuses.get(0).getId());
        }

        return memberDetailsRepo.findById(memberDetailsRepo.save(memberDetail).getId()).get();
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
