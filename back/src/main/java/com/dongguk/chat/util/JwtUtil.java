package com.dongguk.chat.util;

import com.dongguk.chat.security.dto.JwtToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private static final String authorizationHeader = "Authorization";
    private static final String authorizationKey = "auth";
    private static final  String BEARER = "Bearer ";
    private static final long ACCESS_TOKEN_EXPIRED =  20 * 60 * 1000L;

    @Value("${JWT_SECRET}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init(){
        byte[] decode = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(decode);
    }


    public boolean validToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SecurityException | MalformedJwtException e){
            log.error("Invalid Jwt Signature, 유효허지 않은 Jwt 서명입니다.");
        }catch (ExpiredJwtException e){
            log.error(e.getMessage());
            log.error("유효기간이 만료된 Jwt Token 입니다.");
        }catch (UnsupportedJwtException e){
            log.error("지원하지 않는 Jwt Token 입니다.");
        }catch (IllegalArgumentException e){
            log.error("{}", e.getMessage());
        }
        return false;
    }

    public String generateAccessToken(Long userId){
        log.info("{} ------------ Client AccessToken Generation", userId);
        Date date = new Date();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim(authorizationKey, "ROLE_AUTH")
                .setIssuedAt(new Date())
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_EXPIRED))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // req Header 에서 jwt Token 추출
    public String getJwtToken(HttpServletRequest req){
        String bearerToken = req.getHeader(authorizationHeader);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)){
            return bearerToken.substring(7);
        }
        return null;
    }

    public Claims getClaims(String token){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            log.info("token 재발급 요청");
            return e.getClaims();
        }
    }
}
