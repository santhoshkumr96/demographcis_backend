package com.aurum.demographics.contorller;

import com.aurum.demographics.model.api.DeleteRequest;
import com.aurum.demographics.model.api.PaginationRequest;
import com.aurum.demographics.model.db.*;
import com.aurum.demographics.repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.utils.StylesheetPIHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;
import com.monitorjbl.xlsx.StreamingReader;


import java.io.*;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.Member;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
    CommunityDetailRepository communityDetailRepository;

    @Autowired
    AnnualIncomeRepo annualIncomeRepo;

    @Autowired
    BloodGroupRepo bloodGroupRepo;

    @Autowired
    OccupationRepo occupationRepo;

    @Autowired
    EducationQualificationRepo educationQualificationRepo;

    @Autowired
    MaritalStatusRepo maritalStatusRepo;

    @Autowired
    RelationShipRepo relationShipRepo;

    @Autowired
    DemograhicDetailAuditRepository demograhicDetailAuditRepository;

    public static String UPLOAD_DIRECTORY = "/Users/santhoshkumar/Desktop/testdemoimages";


    @PostMapping("/upload") public void uploadImage(@RequestParam String memId,@RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        String fileName = memId + "-" + new Date().getTime()+file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, fileName);
        fileNames.append(fileName);
        Files.write(fileNameAndPath, file.getBytes());
        MemberDetail memberDetail = memberDetailsRepo.findById(Long.parseLong(memId)).get();
        memberDetail.setImageLocation(fileNameAndPath.toString());
        memberDetailsRepo.save(memberDetail);
    }

    @GetMapping("/getImage") public  ResponseEntity<byte[]> getImage(@RequestParam String memId) throws IOException {
        MemberDetail memberDetail = memberDetailsRepo.findById(Long.parseLong(memId)).get();
        System.out.println(memberDetail.getImageLocation());
        InputStream in = getClass().getResourceAsStream(memberDetail.getImageLocation());
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(memberDetail.getImageLocation()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

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

    @GetMapping("/getOccupation")
//    @PreAuthorize("hasRole('USER')")
    public Object getOccupation(){
        return occupationRepo.findAll();
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

    @GetMapping("/getCommunity")
//    @PreAuthorize("hasRole('USER')")
    public Object getCommunity(){
        return communityDetailRepository.findAll();
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

        if (memberDetail.getOccupation() == 0 ){
            List<Occupation> occupations = (List<Occupation>) occupationRepo.findAll();
            memberDetail.setOccupation((int)(long)occupations.get(0).getId());
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

        if (memberDetail.getCommunity() == 0){
            List<CommunityDetail> communityDetails = (List<CommunityDetail>) communityDetailRepository.findAll();
            memberDetail.setCommunity((int)(long)communityDetails.get(0).getId());
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

    @GetMapping("/excel")
    public void downloadExcel() throws FileNotFoundException, JsonProcessingException {
        InputStream is = new FileInputStream(new File("/Users/santhoshkumar/Downloads/Feb03022023.xlsx"));

        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096)// buffer size to use when reading InputStream to file (defaults to 1024)
                .open(is);

        List<MemberDetail> memberDetails = new ArrayList<>();

        List<Gender> genders = new ArrayList<>();
        Gender temp = new Gender();
        temp.setType("n/a");
        genders.add(temp);

        List<RelationShip> relationShips = new ArrayList<>();
        RelationShip temp1 = new RelationShip();
        temp1.setType("n/a");
        relationShips.add(temp1);

        List<MaritalStatus> maritalStatuses = new ArrayList<>();
        MaritalStatus temp2 = new MaritalStatus();
        temp2.setType("n/a");
        maritalStatuses.add(temp2);

        List<BloodGroup> bloodGroups = new ArrayList<>();
        BloodGroup temp3 = new BloodGroup();
        temp3.setType("n/a");
        bloodGroups.add(temp3);

        List<Education> educations = new ArrayList<>();
        Education temp4 = new Education();
        temp4.setType("n/a");
        educations.add(temp4);

        List<CommunityDetail> communityDetails = new ArrayList<>();
        CommunityDetail temp5 = new CommunityDetail();
        temp5.setCommunity("n/a");
        temp5.setCaste("n/a");
        communityDetails.add(temp5);

        List<Occupation> occupations = new ArrayList<>();
        Occupation temp6 = new Occupation();
        temp6.setType("n/a");
        occupations.add(temp6);

        List<StatusOfHouse> statusOfHouses = new ArrayList<>();
        StatusOfHouse temp7 = new StatusOfHouse();
        temp7.setType("n/a");
        statusOfHouses.add(temp7);

        List<TypeOfHouse> typeOfHouses = new ArrayList<>();
        TypeOfHouse temp8 = new TypeOfHouse();
        temp8.setType("n/a");
        typeOfHouses.add(temp8);

        List<DemographicDetail> demographicDetails = new ArrayList<>();
        DemographicDetail demographicDetail = new DemographicDetail();
        demographicDetail.setAreaCode(0);
        demographicDetail.setDistrict("n/a");
        demographicDetail.setBlockName("n/a");
        demographicDetail.setPanchayat("n/a");
        demographicDetail.setTaluk("n/a");
        demographicDetail.setVillageCode("n/a");
        demographicDetail.setVillageName("n/a");
        demographicDetail.setBlockName("n/a");
        demographicDetail.setStreetName("n/a");
        demographicDetails.add(demographicDetail);

        Set<String> gender = new HashSet<>();
        Set<String> relationShip = new HashSet<>();
        Set<String> maritalStatus = new HashSet<>();
        Set<String> bloodGroup = new HashSet<>();
        Set<String> education = new HashSet<>();
        Set<String> communityDetail = new HashSet<>();
        Set<String> occupation = new HashSet<>();
        Set<String> typeOfHouse = new HashSet<>();
        Set<String> statusOfHouse = new HashSet<>();
        Set<String> areaDetails = new HashSet<>();

        int i = 0 ;
        for (Sheet sheet : workbook){
            for (Row r : sheet) {
                i++;
                if(i>2){
                    MemberDetail memberDetail = new MemberDetail();
                    memberDetail.setMemberName(r.getCell(17).getStringCellValue());
                    memberDetail.setFamilyIdRef(r.getCell(15).getStringCellValue());
//                    System.out.println(r.getCell(0).getStringCellValue());
//                    memberDetails.add(memberDetail);


                    gender.add(r.getCell(18).getStringCellValue());
                    relationShip.add(r.getCell(19).getStringCellValue());
                    maritalStatus.add(r.getCell(24).getStringCellValue());
//                    bloodGroup.add(r.getCell(18).getStringCellValue());
                    education.add(r.getCell(23).getStringCellValue());
                    communityDetail.add(r.getCell(13).getStringCellValue()+"|"+r.getCell(14).getStringCellValue());
                    occupation.add(r.getCell(25).getStringCellValue());
                    typeOfHouse.add(r.getCell(34).getStringCellValue());
                    statusOfHouse.add(r.getCell(33).getStringCellValue());
                    areaDetails.add(
                            r.getCell(1).getStringCellValue()+"|"+
                            r.getCell(2).getStringCellValue()+"|"+
                            r.getCell(3).getStringCellValue()+"|"+
                            r.getCell(4).getStringCellValue()+"|"+
                            r.getCell(5).getStringCellValue()+"|"+
                            r.getCell(6).getStringCellValue()+"|"+
                            r.getCell(7).getStringCellValue()+"|"+
                            r.getCell(8).getStringCellValue()
                    );
                }
                if(i%10000 == 0){
//                    memberDetailsRepo.saveAll(memberDetails);
//                    memberDetails = new ArrayList<>();
                    System.out.println("uploaded number of data==>"+ i);
                }
            }
        }


//        System.out.println(new ObjectMapper().writeValueAsString(gender));
//        System.out.println(new ObjectMapper().writeValueAsString(relationShip));
//        System.out.println(new ObjectMapper().writeValueAsString(maritalStatus));
//        System.out.println(new ObjectMapper().writeValueAsString(communityDetail));
//        System.out.println(new ObjectMapper().writeValueAsString(occupation));
//        System.out.println(new ObjectMapper().writeValueAsString(bloodGroup));
//        System.out.println(new ObjectMapper().writeValueAsString(education));
        for (String g: gender){
            if(!g.toLowerCase(Locale.ROOT).isEmpty()){
                Gender gen = new Gender();
                gen.setType(g.toLowerCase(Locale.ROOT));
                genders.add(gen);
            }
        }
        genderRepo.saveAll(genders);

        for (String g: relationShip){
            if(!g.toLowerCase(Locale.ROOT).isEmpty()){
                RelationShip gen = new RelationShip();
                gen.setType(g.toLowerCase(Locale.ROOT));
                relationShips.add(gen);
            }
        }

        for (String g: maritalStatus){
            if(!g.toLowerCase(Locale.ROOT).isEmpty()){
                MaritalStatus gen = new MaritalStatus();
                gen.setType(g.toLowerCase(Locale.ROOT));
                maritalStatuses.add(gen);
            }
        }


        for (String g: occupation){
            if(!g.toLowerCase(Locale.ROOT).isEmpty()){
                Occupation gen = new Occupation();
                gen.setType(g.toLowerCase(Locale.ROOT));
                occupations.add(gen);
            }
        }

        for (String g: bloodGroup){
            if(!g.toLowerCase(Locale.ROOT).isEmpty()){
                BloodGroup gen = new BloodGroup();
                gen.setType(g.toLowerCase(Locale.ROOT));
                bloodGroups.add(gen);
            }
        }

        for (String g: education){
            if(!g.toLowerCase(Locale.ROOT).isEmpty()){
                Education gen = new Education();
                gen.setType(g.toLowerCase(Locale.ROOT));
                educations.add(gen);
            }
        }

        for (String g: typeOfHouse){
            if(!g.toLowerCase(Locale.ROOT).isEmpty()){
                TypeOfHouse gen = new TypeOfHouse();
                gen.setType(g.toLowerCase(Locale.ROOT));
                typeOfHouses.add(gen);
            }
        }

        for (String g: statusOfHouse){
            if(!g.toLowerCase(Locale.ROOT).isEmpty()){
                StatusOfHouse gen = new StatusOfHouse();
                gen.setType(g.toLowerCase(Locale.ROOT));
                statusOfHouses.add(gen);
            }
        }

        for (String g: communityDetail){
            if(!g.toLowerCase(Locale.ROOT).isEmpty()){
                CommunityDetail gen = new CommunityDetail();
                String[] arrOfStr = g.split("\\|");
                gen.setCommunity(arrOfStr[0].toLowerCase(Locale.ROOT));
                gen.setCaste(arrOfStr[1].toLowerCase(Locale.ROOT));
                communityDetails.add(gen);
            }
        }

        for (String g: areaDetails){
            if(!g.toLowerCase(Locale.ROOT).isEmpty()){
                DemographicDetail gen = new DemographicDetail();
                String[] arrOfStr = g.split("\\|");
                gen.setDistrict(arrOfStr[0]);
                gen.setTaluk(arrOfStr[1]);
                gen.setBlockName(arrOfStr[2]);
                gen.setPanchayat(arrOfStr[3]);
                gen.setAreaCode(Integer.parseInt(arrOfStr[4]));
                gen.setVillageCode(arrOfStr[5]);
                gen.setVillageName(arrOfStr[6]);
                gen.setStreetName(arrOfStr[7]);
                if(gen.getVillageName().isEmpty() || Objects.isNull(gen.getVillageName())){
                    System.out.println(new ObjectMapper().writeValueAsString(arrOfStr));
                }
                demographicDetails.add(gen);
            }
        }


        demograhicDetailRepository.deleteAll();
        demograhicDetailRepository.saveAll(demographicDetails);
        genderRepo.deleteAll();
        genderRepo.saveAll(genders);
        relationShipRepo.deleteAll();
        relationShipRepo.saveAll(relationShips);
        maritalStatusRepo.deleteAll();
        maritalStatusRepo.saveAll(maritalStatuses);
        bloodGroupRepo.deleteAll();
        bloodGroupRepo.saveAll(bloodGroups);
        occupationRepo.deleteAll();
        occupationRepo.saveAll(occupations);
        typeOfHouseRepo.deleteAll();
        typeOfHouseRepo.saveAll(typeOfHouses);
        statusOfHouseRepo.deleteAll();
        statusOfHouseRepo.saveAll(statusOfHouses);
        communityDetailRepository.deleteAll();
        communityDetailRepository.saveAll(communityDetails);
        educationQualificationRepo.deleteAll();
        educationQualificationRepo.saveAll(educations);
        memberDetailsRepo.deleteAll();
        familyDetailRepository.deleteAll();
//        memberDetailsRepo.saveAll(memberDetails);
    }

}
