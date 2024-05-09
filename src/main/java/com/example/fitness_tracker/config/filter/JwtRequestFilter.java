package com.example.fitness_tracker.config.filter;


import com.example.fitness_tracker.data.constants.Constants;
import com.example.fitness_tracker.data.entity.User;
import com.example.fitness_tracker.data.enums.Roles;
import com.example.fitness_tracker.data.pojo.UserDetails;
import com.example.fitness_tracker.exceptions.ApiException;
import com.example.fitness_tracker.service.JwtUserDetailService;
import com.example.fitness_tracker.utils.JwtTokenUtil;
import com.example.fitness_tracker.utils.LocaleUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LocaleUtil localeUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        Roles role = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                role=jwtTokenUtil.getRoleFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.error("Unable to get JWT Token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            } catch (ExpiredJwtException e)
            {
                log.error("JWT Token has expired");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
            catch (Exception exception)
            {
                log.error(exception.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
                return;
            }
        } else {
            log.warn("JWT Token does not begin with Bearer String");
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.jwtUserDetailsService.getUserByRole(username,role);
                // if token is valid configure Spring Security to manually set
                // authentication
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                   // authObject=new AuthObject();

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    request.setAttribute(Constants.USER_OBJECT,userDetails);

                }
            } catch (ApiException e) {
                log.error(e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
