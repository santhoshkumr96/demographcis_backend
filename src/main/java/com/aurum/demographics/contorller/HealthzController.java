package com.aurum.demographics.contorller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class HealthzController {

    @GetMapping("/healthz")
    public void healthz(){
        Logger log = LoggerFactory.getLogger(HealthzController.class);
        log.info("Demographics backend application is up and running");
    }


    @GetMapping("/testLogin")
    @PreAuthorize("hasRole('USER')")
    public void healthzTestLogin(){
        Logger log = LoggerFactory.getLogger(HealthzController.class);
        log.info("Login flow is working");
    }

}
