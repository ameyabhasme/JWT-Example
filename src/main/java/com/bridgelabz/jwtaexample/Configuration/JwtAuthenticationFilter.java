package com.bridgelabz.jwtaexample.Configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.jwtaexample.Helper.JwtUtil;
import com.bridgelabz.jwtaexample.Services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        /**
         * get jwt Bearer Pattern check then validation
         */

        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        // Checking null and Format

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) 
        {
            jwtToken = requestTokenHeader.substring(8);

            try {
                username = this.jwtUtil.getUsernameFromToken(jwtToken);
            } catch (Exception e) {
                e.printStackTrace();
            }

        
        // Security Authentication and setup of Authentication Token
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }else{
            System.out.println("Token is InValid....");
            }
        }
        filterChain.doFilter(request, response);
    }
}
