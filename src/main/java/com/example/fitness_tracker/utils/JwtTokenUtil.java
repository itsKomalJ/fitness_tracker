package com.example.fitness_tracker.utils;

import com.example.fitness_tracker.data.entity.User;
import com.example.fitness_tracker.data.enums.Roles;
import com.example.fitness_tracker.data.pojo.UserDetails;
import com.example.fitness_tracker.exceptions.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.example.fitness_tracker.data.constants.Constants.TOKEN_EXPIRED;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private LocaleUtil localeUtil;

    public static final long JWT_TOKEN_VALIDITY = 4320 * 60 * 60;

    public String generateToken(String userName, String role, long id){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",userName);
        claims.put("role",role);
        claims.put("id",id);
        return doGenerateToken(claims, userName);

    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getUsernameFromToken(String token) throws ApiException {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Roles getRoleFromToken(String token) throws ApiException {
        return Roles.valueOf(getAllClaimsFromToken(token).get("role").toString());
    }
    public Date getExpirationDateFromToken(String token) throws ApiException {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws ApiException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws ApiException {

        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException e){
            log.error("JWT Token has expired");
            throw new ApiException(localeUtil.getLocalizedString(TOKEN_EXPIRED), HttpStatus.UNAUTHORIZED);
        }
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) throws ApiException {
        final String username = getUsernameFromToken(token);
        log.info("Username: "+username);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) throws ApiException {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

}
