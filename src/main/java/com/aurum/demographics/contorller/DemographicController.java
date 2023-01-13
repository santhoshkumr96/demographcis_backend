package com.aurum.demographics.contorller;

import com.aurum.demographics.repo.DemograhicDetailRepository;
import com.aurum.demographics.repo.FamilyDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
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

    @GetMapping("/getFamilyDetails")
    @PreAuthorize("hasRole('USER')")
    public Object getFamilyDetails(){
        return familyDetailRepository.findAll();
    }

}
