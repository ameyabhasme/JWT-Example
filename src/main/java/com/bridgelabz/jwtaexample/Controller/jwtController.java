package com.bridgelabz.jwtaexample.Controller;

import com.bridgelabz.jwtaexample.Helper.JwtUtil;
import com.bridgelabz.jwtaexample.Model.JwtResponse;
import com.bridgelabz.jwtaexample.Model.jwtRequest;
import com.bridgelabz.jwtaexample.Services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class jwtController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody jwtRequest jwtRequest) throws Exception{
       System.out.println(jwtRequest); 
       try {
           this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
           
       } catch (UsernameNotFoundException e) {
           e.printStackTrace();
           throw new Exception("Bad Credentials");
       }
       //required when we generate token and if Authenticated then this block will be excecuted.
       UserDetails userDetails= this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
       String token =this.jwtUtil.generateToken(userDetails);
       System.out.println("JWT TOKEN " + token);
       
       //{"token": "value"}
       return ResponseEntity.ok(new JwtResponse(token));
    }
}
