package com.example.fitness_tracker.config.filter;

import com.example.fitness_tracker.data.constants.Constants;
import com.example.fitness_tracker.data.entity.User;
import com.example.fitness_tracker.exceptions.ApiException;
import com.example.fitness_tracker.service.JwtUserDetailService;
import com.example.fitness_tracker.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class HeaderCheckFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
//        AeroUserDetails adminUserDetails = jwtUserDetailsService.getUserbyRole(Constants.ORIGINAL_ADMIN_DETAILS, UserTypes.ADMIN, UserSubRoles.SUPERADMIN);
        request.setAttribute(Constants.CORRELATION_ID, UUID.randomUUID().toString());
        String correlationId = request.getHeader(Constants.CORRELATION_ID);    // GETTING Correlation data from requests
        if ((correlationId != null) && (!correlationId.matches(Constants.UUID_FORMAT))) {
            log.error(Constants.INVALID_CORRELATION_ID);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, Constants.INVALID_CORRELATION_ID);
            return;

        }
        chain.doFilter(request, response);
    }
}
