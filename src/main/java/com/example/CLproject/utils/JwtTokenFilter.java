package com.example.CLproject.utils;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.CLproject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        //Extract JWT token from the Authorization header
        String token = extractToken(request);
        
        if(token != null && jwtTokenUtil.validateAccessToken(token)) {
            //Extract User details from the token
            UserDetails userDetails = getUserDetails(token);


            //Create authentication token
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //Set authentication in SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        //continue the filter execution
        filterChain.doFilter(request, response);

    }


    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if( authHeader != null && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);

        }
        return null;
    }

    private UserDetails getUserDetails(String token){
        User userDetails = new User();

        userDetails.setId(jwtTokenUtil.extractUserId(token));
        userDetails.setUsername(jwtTokenUtil.extractUsername(token));
        userDetails.setRole(User.Role.valueOf(jwtTokenUtil.extractRole(token).toUpperCase()));  // Assuming roles are capitalized

        System.out.println(userDetails);

        return userDetails;
    }

}
