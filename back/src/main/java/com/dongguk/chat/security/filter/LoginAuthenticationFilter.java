package com.dongguk.chat.security.filter;

import com.dongguk.chat.domain.user.User;
import com.dongguk.chat.domain.user.dto.UserResponseDto;
import com.dongguk.chat.security.dto.JwtToken;
import com.dongguk.chat.security.dto.UserLoginDto;
import com.dongguk.chat.security.service.UserDetailsImpl;
import com.dongguk.chat.util.JwtUtil;
import com.dongguk.chat.util.ResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ResponseWrapper responseWrapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            System.out.println();
            UserLoginDto userLoginDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);

            log.error(userLoginDto.toString());

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getUserLoginId(),
                            userLoginDto.getUserPassword())
            );
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getUsers().getId();

        String accessToken = jwtUtil.generateAccessToken(userId);
        JwtToken jwtToken = JwtToken.builder()
                .accessToken(accessToken)
                .userResponseDto(UserResponseDto.fromUser(userDetails.getUsers()))
                .build();


        responseWrapper.convertObjectToResponse(response, jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
//        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.USER_NOT_FOUND_ERROR);
//        responseWrapper.convertObjectToResponse(response, errorResponse);
    }
}
