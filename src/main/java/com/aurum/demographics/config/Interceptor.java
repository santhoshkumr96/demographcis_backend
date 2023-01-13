package com.aurum.demographics.config;

import com.aurum.demographics.model.db.UserAuditTable;
import com.aurum.demographics.repo.UserAuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class Interceptor implements HandlerInterceptor {

    @Autowired
    UserAuditRepository userAuditRepository;

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object object, ModelAndView model) {
        try {
            if (!request.getRequestURI().equals("/api/auth/signup")) {
                UserAuditTable userAuditTable = new UserAuditTable();
                userAuditTable.setUsername(getUserNameFromContext());
                userAuditTable.setUri(request.getRequestURI());
                userAuditRepository.save(userAuditTable);
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }
    private String getUserNameFromContext(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        return username;
    }

}
