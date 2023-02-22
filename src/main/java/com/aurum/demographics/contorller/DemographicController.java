package com.aurum.demographics.contorller;

import com.aurum.demographics.model.api.DeleteRequest;
import com.aurum.demographics.model.api.PaginationRequest;
import com.aurum.demographics.model.db.*;
import com.aurum.demographics.repo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.monitorjbl.xlsx.StreamingReader;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    FamilyDetailRepositoryForGet familyDetailRepositoryForGet;

    @Autowired
    MemberDetailForFamIdUpdateRepo memberDetailForFamIdUpdateRepo;

    @Autowired
    FamilyDetailAuditRepository familyDetailAuditRepository;

    @Autowired
    HandiCapeTypeRepo handiCapeTypeRepo;


    @Autowired
    MemberDetailsAuditRepo memberDetailsAuditRepo;

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

    @GetMapping("/getHandiCapDetails")
//    @PreAuthorize("hasRole('USER')")
    public Object getHandiCapDetails(){
        return handiCapeTypeRepo.findAll();
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
        return familyDetailRepositoryForGet.findByFamilyIdContainingAndRespondentNameContainingAndMobileNumberContainingAndDemographicDetailVillageNameContainingAndIsDeleted(
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

        if(!familyDetails.getFamilyId().isEmpty()){
            FamilyDetails familyDetailsAudit = familyDetailRepository.findById(familyDetails.getId()).get();
            FamilyDetailsAudit familyDetailsAudit1 = new FamilyDetailsAudit();
            BeanUtils.copyProperties(familyDetailsAudit,familyDetailsAudit1);
//            System.out.println(new ObjectMapper().writeValueAsString(familyDetailsAudit1));
            familyDetailAuditRepository.save(familyDetailsAudit1);

        }

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
               demographicDetailAudit.setFamilyId(familyDetails.getId().toString());
               demograhicDetailAuditRepository.save(demographicDetailAudit);

               DemographicDetail demographicDetail = demograhicDetailRepository.findById((long)familyDetails.getAreaDetails()).get();
               List<FamilyDetailsForGet> familyDetailsForGets =  familyDetailRepositoryForGet.findByFamilyIdContainingOrderByFamilyIdDesc(demographicDetail.getAreaCode()+demographicDetail.getVillageCode());
               log.info("area is changed"+familyDetailsForGets.size());
               if(familyDetailsForGets.size() > 0){
                   log.info(new ObjectMapper().writeValueAsString(familyDetailsForGets.get(0)));
                   String familyId = familyDetailsForGets.get(0).getFamilyId();
                   String numberOFfamilyInArea = familyId.substring(familyId.length() - 4);
                   String famNum = String.format("%04d",Integer.parseInt(numberOFfamilyInArea)+1);
                   List<MemberDetailForFamIdUpdate> memberDetailForFamIdUpdates =  memberDetailForFamIdUpdateRepo.findByFamilyIdRef(familyDetails.getFamilyId());
                   for(MemberDetailForFamIdUpdate mem : memberDetailForFamIdUpdates){
                       mem.setFamilyIdRef(demographicDetail.getAreaCode()+demographicDetail.getVillageCode()+famNum);
                   }
                   memberDetailForFamIdUpdateRepo.saveAll(memberDetailForFamIdUpdates);
                   familyDetails.setFamilyId(demographicDetail.getAreaCode()+demographicDetail.getVillageCode()+famNum);
               } else {
                   String famNum = String.format("%04d",1);
                   List<MemberDetailForFamIdUpdate> memberDetailForFamIdUpdates =  memberDetailForFamIdUpdateRepo.findByFamilyIdRef(familyDetails.getFamilyId());
                   for(MemberDetailForFamIdUpdate mem : memberDetailForFamIdUpdates){
                       mem.setFamilyIdRef(demographicDetail.getAreaCode()+demographicDetail.getVillageCode()+famNum);
                   }
                   memberDetailForFamIdUpdateRepo.saveAll(memberDetailForFamIdUpdates);
                   familyDetails.setFamilyId(demographicDetail.getAreaCode()+demographicDetail.getVillageCode()+famNum);
               }
           }
        }
        if(Objects.isNull(familyDetails.getFamilyId()) || familyDetails.getFamilyId().isEmpty()){
            DemographicDetail demographicDetail = demograhicDetailRepository.findById((long)familyDetails.getAreaDetails()).get();
            List<FamilyDetailsForGet> familyDetailsForGets =  familyDetailRepositoryForGet.findByFamilyIdContainingOrderByFamilyIdDesc(demographicDetail.getAreaCode()+demographicDetail.getVillageCode());
            String famNum = "";
            if(familyDetailsForGets.size() > 0){
                String familyId = familyDetailsForGets.get(0).getFamilyId();
                String numberOFfamilyInArea = familyId.substring(familyId.length() - 4);
                 famNum = String.format("%04d",Integer.parseInt(numberOFfamilyInArea)+1);
            }else{
                 famNum = String.format("%04d",1);
            }
            familyDetails.setFamilyId(demographicDetail.getAreaCode()+demographicDetail.getVillageCode()+famNum);
            log.info("it is a new enty");
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

        if( !Objects.isNull(memberDetail.getId()) && memberDetail.getId() != 0){
            MemberDetail memberDetailAudit = memberDetailsRepo.findById(memberDetail.getId()).get();
            MemberDetailAudit memberDetailAudit1 = new MemberDetailAudit();
            BeanUtils.copyProperties(memberDetailAudit,memberDetailAudit1);
            memberDetailsAuditRepo.save(memberDetailAudit1);
        }

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

        if (memberDetail.getHandicapType() == 0 ){
            List<HandicapType> educations = (List<HandicapType>) handiCapeTypeRepo.findAll();
            memberDetail.setHandicapType((int)(long)educations.get(0).getId());
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

    @GetMapping("/excelDropdowns")
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

        List<HandicapType> handicapTypes = new ArrayList<>();
        HandicapType handicapType = new HandicapType();
        handicapType.setType("n/a");
        handicapTypes.add(handicapType);

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
        Set<String> handiCapeTypeset = new HashSet<>();

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


                    handiCapeTypeset.add(r.getCell(99).getStringCellValue());
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
                gen.setDistrict(arrOfStr[0].toLowerCase(Locale.ROOT));
                gen.setTaluk(arrOfStr[1].toLowerCase(Locale.ROOT));
                gen.setBlockName(arrOfStr[2].toLowerCase(Locale.ROOT));
                gen.setPanchayat(arrOfStr[3].toLowerCase(Locale.ROOT));
                gen.setAreaCode(Integer.parseInt(arrOfStr[4]));
                gen.setVillageCode(arrOfStr[5].toLowerCase(Locale.ROOT));
                gen.setVillageName(arrOfStr[6].toLowerCase(Locale.ROOT));
                gen.setStreetName(arrOfStr[7].toLowerCase(Locale.ROOT));
                if(gen.getVillageName().isEmpty() || Objects.isNull(gen.getVillageName())){
                    System.out.println(new ObjectMapper().writeValueAsString(arrOfStr));
                }
                demographicDetails.add(gen);
            }
        }

        for (String g: handiCapeTypeset){
            if(!g.toLowerCase(Locale.ROOT).isEmpty()){
                HandicapType gen = new HandicapType();
                gen.setType(g.toLowerCase(Locale.ROOT));
                handicapTypes.add(gen);
            }
        }



        handiCapeTypeRepo.deleteAll();
        handiCapeTypeRepo.saveAll(handicapTypes);
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


    @GetMapping("/excelDataFamily")
    public void downloadExcelData() throws FileNotFoundException, JsonProcessingException {
        InputStream is = new FileInputStream(new File("/Users/santhoshkumar/Downloads/Feb03022023.xlsx"));

        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096)// buffer size to use when reading InputStream to file (defaults to 1024)
                .open(is);

        List<MemberDetail> memberDetails = new ArrayList<>();

        List<Gender> genders = new ArrayList<>();
        List<RelationShip> relationShips = new ArrayList<>();
        List<MaritalStatus> maritalStatuses = new ArrayList<>();
        List<BloodGroup> bloodGroups = new ArrayList<>();
        List<Education> educations = new ArrayList<>();
        List<CommunityDetail> communityDetails = new ArrayList<>();
        List<Occupation> occupations = new ArrayList<>();
        List<StatusOfHouse> statusOfHouses = (List<StatusOfHouse>) statusOfHouseRepo.findAll();
        List<TypeOfHouse> typeOfHouses = (List<TypeOfHouse>) typeOfHouseRepo.findAll();
        List<DemographicDetail> demographicDetails = (List<DemographicDetail>) demograhicDetailRepository.findAll();

        List<FamilyDetails> familyDetails = new ArrayList<>();
        Map<String, String> famIdMap = new HashMap<>();

        Map<String, Integer> stringStatusOfHouseMap = new HashMap<>();
        for(StatusOfHouse statusOfHouse: statusOfHouses){
            stringStatusOfHouseMap.put(statusOfHouse.getType(),((int)(long) statusOfHouse.getId()));
        }

        Map<String, Integer> stringTypeOfHouseHashMap = new HashMap<>();
        for(TypeOfHouse typeOfHouse: typeOfHouses){
            stringTypeOfHouseHashMap.put(typeOfHouse.getType(), ((int)(long) typeOfHouse.getId()));
        }

        Map<String, Integer> stringDemographicDetailMap = new HashMap<>();
        for(DemographicDetail demographicDetail: demographicDetails){
            stringDemographicDetailMap.put(
                    demographicDetail.getDistrict()+""+
                            demographicDetail.getTaluk()+""+
                            demographicDetail.getBlockName()+""+
                            demographicDetail.getPanchayat()+""+
                            demographicDetail.getAreaCode()+""+
                            demographicDetail.getVillageCode()+""+
                            demographicDetail.getVillageName()+""+
                            demographicDetail.getStreetName()
                    ,
                    ((int)(long) demographicDetail.getId()));
        }
        int i = 0 ;
        try{
            for (Sheet sheet : workbook){
                for (Row r : sheet) {
                    i++;
                    if(i>2){

                        if(!famIdMap.containsKey(r.getCell(15).getStringCellValue())){
                            FamilyDetails familyDetail = new FamilyDetails();
                            familyDetail.setFamilyId(r.getCell(15).getStringCellValue());
                            familyDetail.setRespondentName(r.getCell(10).getStringCellValue());
                            familyDetail.setMobileNumber(r.getCell(11).getStringCellValue());
                            familyDetail.setDoorNo(r.getCell(9).getStringCellValue());
                            familyDetail.setIsDeleted("N");

                            if(Objects.isNull(r.getCell(33)) || Objects.isNull(r.getCell(33).getStringCellValue())  || r.getCell(33).getStringCellValue().isEmpty()){
                                familyDetail.setStatusOfHouse(stringStatusOfHouseMap.get("n/a"));
                            }else{
                                familyDetail.setStatusOfHouse(stringStatusOfHouseMap.get(r.getCell(33).getStringCellValue().toLowerCase(Locale.ROOT)));
                            }

                            if(Objects.isNull(r.getCell(34)) || Objects.isNull(r.getCell(34).getStringCellValue())  || r.getCell(34).getStringCellValue().isEmpty()){
                                familyDetail.setTypeOfHouse(stringTypeOfHouseHashMap.get("n/a"));
                            }else{
                                familyDetail.setTypeOfHouse(stringTypeOfHouseHashMap.get(r.getCell(34).getStringCellValue().toLowerCase(Locale.ROOT)));

                            }

                            familyDetail.setCreatedBy(1);
                            familyDetail.setAreaDetails(
                                    stringDemographicDetailMap.get(
                                            r.getCell(1).getStringCellValue().toLowerCase(Locale.ROOT)+
                                                    r.getCell(2).getStringCellValue().toLowerCase(Locale.ROOT)+
                                                    r.getCell(3).getStringCellValue().toLowerCase(Locale.ROOT)+
                                                    r.getCell(4).getStringCellValue().toLowerCase(Locale.ROOT)+
                                                    r.getCell(5).getStringCellValue().toLowerCase(Locale.ROOT)+
                                                    r.getCell(6).getStringCellValue().toLowerCase(Locale.ROOT)+
                                                    r.getCell(7).getStringCellValue().toLowerCase(Locale.ROOT)+
                                                    r.getCell(8).getStringCellValue().toLowerCase(Locale.ROOT)

                                    )
                            );
                            if(r.getCell(39).getStringCellValue().toLowerCase(Locale.ROOT).equals("yes")){
                                familyDetail.setToiletFacilityAtHome("Y");
                            } else if (r.getCell(39).getStringCellValue().toLowerCase(Locale.ROOT).equals("no")){
                                familyDetail.setToiletFacilityAtHome("N");
                            } else {
                                familyDetail.setToiletFacilityAtHome("");
                            }

                            familyDetail.setWetLandInAcres(r.getCell(35).getStringCellValue());
                            familyDetail.setDryLandInAcres(r.getCell(36).getStringCellValue());



                            famIdMap.put(r.getCell(15).getStringCellValue(),"");
                            familyDetails.add(familyDetail);
                        }
                    }
                    if(i%1000 == 0){
                        familyDetailRepository.saveAll(familyDetails);
                        familyDetails.clear();
                        System.out.println("uploaded number of data==>"+ i);
                    }
                }
            }
        } catch (Exception e){
            System.out.println("error"+e.getMessage());
            e.printStackTrace();
        }
        System.out.println("last data read==>"+ i);
        familyDetailRepository.saveAll(familyDetails);
    }

    @GetMapping("/excelDataMem")
    public void downloadExcelDataMem() throws FileNotFoundException, JsonProcessingException {
        InputStream is = new FileInputStream(new File("/Users/santhoshkumar/Downloads/Feb03022023.xlsx"));

        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096)// buffer size to use when reading InputStream to file (defaults to 1024)
                .open(is);

        List<MemberDetail> memberDetails = new ArrayList<>();

        List<Gender> genders = (List<Gender>) genderRepo.findAll();
        List<RelationShip> relationShips = (List<RelationShip>) relationShipRepo.findAll();
        List<MaritalStatus> maritalStatuses = (List<MaritalStatus>) maritalStatusRepo.findAll();
        List<BloodGroup> bloodGroups = (List<BloodGroup>) bloodGroupRepo.findAll();
        List<Education> educations = (List<Education>) educationQualificationRepo.findAll();
        List<CommunityDetail> communityDetails = (List<CommunityDetail>) communityDetailRepository.findAll();
        List<Occupation> occupations = (List<Occupation>) occupationRepo.findAll();
        List<HandicapType> handicapTypes = (List<HandicapType>) handiCapeTypeRepo.findAll();

        Map<String, Integer> handicapTypesMap = new HashMap<>();
        for(HandicapType data: handicapTypes){
            handicapTypesMap.put(data.getType(),((int)(long) data.getId()));
        }

        Map<String, Integer> genMap = new HashMap<>();
        for(Gender data: genders){
            genMap.put(data.getType(),((int)(long) data.getId()));
        }

        Map<String, Integer> relMap = new HashMap<>();
        for(RelationShip data: relationShips){
            relMap.put(data.getType(),((int)(long) data.getId()));
        }

        Map<String, Integer> msMap = new HashMap<>();
        for(MaritalStatus data: maritalStatuses){
            msMap.put(data.getType(),((int)(long) data.getId()));
        }

        Map<String, Integer> bloodGroupsMap = new HashMap<>();
        for(BloodGroup data: bloodGroups){
            bloodGroupsMap.put(data.getType(),((int)(long) data.getId()));
        }

        Map<String, Integer> educationsMap = new HashMap<>();
        for(Education data: educations){
            educationsMap.put(data.getType(),((int)(long) data.getId()));
        }

        Map<String, Integer> occupationsMap = new HashMap<>();
        for(Occupation data: occupations){
            occupationsMap.put(data.getType(),((int)(long) data.getId()));
        }

        Map<String, Integer> communityDetailsMap = new HashMap<>();
        for(CommunityDetail data: communityDetails){
            communityDetailsMap.put(data.getCommunity()+data.getCaste(),((int)(long) data.getId()));
        }


        int i = 0 ;
        try{
            for (Sheet sheet : workbook){
                for (Row r : sheet) {
                    i++;
                    if(i>2){
                        MemberDetail memberDetail = new MemberDetail();

                        memberDetail.setFamilyIdRef(r.getCell(15).getStringCellValue());
                        memberDetail.setMobileNumber(r.getCell(11).getStringCellValue());

                        memberDetail.setBp(yesOrNo(r.getCell(57).getStringCellValue()));
                        memberDetail.setLungRelatedDiseases(yesOrNo(r.getCell(112).getStringCellValue()));
                        memberDetail.setSmartphone(yesOrNo(r.getCell(12).getStringCellValue()));
                        memberDetail.setGovtInsurance(yesOrNo(r.getCell(40).getStringCellValue()));
                        memberDetail.setPrivateInsurance(yesOrNo(r.getCell(41).getStringCellValue()));
                        memberDetail.setOldAgePension(yesOrNo(r.getCell(42).getStringCellValue()));
                        memberDetail.setWidowedPension(yesOrNo(r.getCell(43).getStringCellValue()));
                        memberDetail.setRetiredPerson(yesOrNo(r.getCell(44).getStringCellValue()));
                        memberDetail.setSmoking(yesOrNo(r.getCell(47).getStringCellValue()));
                        memberDetail.setDrinking(yesOrNo(r.getCell(48).getStringCellValue()));
                        memberDetail.setTobacco(yesOrNo(r.getCell(49).getStringCellValue()));
                        memberDetail.setAsthma(yesOrNo(r.getCell(56).getStringCellValue()));
                        memberDetail.setDiabetes(yesOrNo(r.getCell(104).getStringCellValue()));
                        memberDetail.setHeartDiseases(yesOrNo(r.getCell(111).getStringCellValue()));
                        memberDetail.setObesity(yesOrNo(r.getCell(75).getStringCellValue()));
                        memberDetail.setBreastCancer(yesOrNo(r.getCell(107).getStringCellValue()));
                        memberDetail.setUterusCancer(yesOrNo(r.getCell(108).getStringCellValue()));
                        memberDetail.setPhysicallyChallenged(yesOrNo(r.getCell(98).getStringCellValue()));
                        memberDetail.setHandicapType(handicapTypesMap.get(mapValueCheck(r.getCell(99).getStringCellValue())));

                        memberDetail.setCommunity(communityDetailsMap.get(r.getCell(13).getStringCellValue().toLowerCase(Locale.ROOT)+r.getCell(14).getStringCellValue().toLowerCase(Locale.ROOT)));
                        memberDetail.setMemberName(r.getCell(17).getStringCellValue());

                        memberDetail.setGender(genMap.get(mapValueCheck(r.getCell(18).getStringCellValue())));
                        memberDetail.setRelationship(relMap.get(mapValueCheck(r.getCell(19).getStringCellValue())));
                        memberDetail.setEducationQualification(educationsMap.get(mapValueCheck(r.getCell(23).getStringCellValue())));
                        memberDetail.setMaritalStatus(msMap.get(mapValueCheck(r.getCell(24).getStringCellValue())));
                        memberDetail.setOccupation(occupationsMap.get(mapValueCheck(r.getCell(25).getStringCellValue())));
                        memberDetail.setBloodGroup(bloodGroupsMap.get("n/a"));
                        memberDetail.setAnnualIncome(1);

                        memberDetail.setAnnualIncomeString(r.getCell(27).getStringCellValue());
                        memberDetail.setRetiredPerson(yesOrNo(r.getCell(26).getStringCellValue()));
                        memberDetail.setTmh_id(r.getCell(28).getStringCellValue());
                        memberDetail.setPatient_id(r.getCell(29).getStringCellValue());
                        memberDetail.setIsDeceased(yesOrNo(r.getCell(30).getStringCellValue()));

                        memberDetail.setBirthDate(r.getCell(21).getStringCellValue().isEmpty()?null:dateGet(r.getCell(21).getStringCellValue()));
                        memberDetail.setIsDeleted("N");
                        memberDetail.setCreatedBy(1);
                        memberDetails.add(memberDetail);
                    }
                    if(i%1000 == 0){
                        memberDetailsRepo.saveAll(memberDetails);
                        memberDetails.clear();
                        System.out.println("uploaded number of data==>"+ i);
//                        break;
                    }
                }
            }
        } catch (Exception e){
            System.out.println("error"+e.getMessage());
            e.printStackTrace();
        }
        memberDetailsRepo.saveAll(memberDetails);
        System.out.println("last data read==>"+ i);
    }

    public String yesOrNo(String val){
        if(val.toLowerCase(Locale.ROOT).equals("yes")){
            return "Y";
        } else if (val.toLowerCase(Locale.ROOT).equals("no")){
            return "N";
        } else {
            return "";
        }
    }

    public String mapValueCheck(String val){
        if(Objects.isNull(val) || val.isEmpty()){
            return "n/a";
        }else{
            return val.toLowerCase(Locale.ROOT);
        }

    }

    public Date dateGet(String val) throws JsonProcessingException, ParseException {
        String[] datestrings = val.split("-");
//        System.out.println(new ObjectMapper().writeValueAsString(datestrings));
        Date date = new GregorianCalendar(
                Integer.parseInt(datestrings[2]),  Integer.parseInt(datestrings[0]) - 1, Integer.parseInt(datestrings[1])).getTime();

        java.sql.Timestamp sq = new java.sql.Timestamp(date.getTime());
        String dateStr = new SimpleDateFormat("yyyy-MM-dd")
                .format(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // your template here
        java.util.Date dateStrAct = formatter.parse(dateStr);
        java.sql.Date dateDB = new java.sql.Date(dateStrAct.getTime());
        return new Date(dateStrAct.getTime());
    }

}
